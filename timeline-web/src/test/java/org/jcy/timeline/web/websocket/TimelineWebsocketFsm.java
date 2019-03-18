package org.jcy.timeline.web.websocket;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.web.Application;
import org.jcy.timeline.web.ItemFactory;
import org.jcy.timeline.web.WebFsmTestRunner;
import org.jcy.timeline.web.model.RegisterResponse;
import org.jcy.timeline.web.model.UpdateInfo;
import org.jcy.timeline.web.service.TimelineService;
import org.jcy.timeline.web.ui.WebAutoUpdate;
import org.jcy.timeline.web.ui.WebTimeline;
import org.jcy.timeline.web.ui.WebTimelineFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimelineWebsocketFsm implements FsmModel {

    @LocalServerPort
    private int port;

    private static final String SESSION_ID = "aaa";
    private static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";

    private static final String PROJECT_NAME = "jenkins-web-test";

    private static final String WEBSOCKET_URI = "ws://localhost:{port}/websocket";
    private static final String WEBSOCKET_SEND_TOPIC = "/msg/startAutoUpdate";
    private static final String WEBSOCKET_RECEIVE_TOPIC = "/user/unicast/autoUpdate";

    private static final int newCount = 10;
    private static final List<GitItem> newItems = Lists.newArrayList(ItemFactory.createNewItems(1, 10));


    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }

    private enum State {START, AUTO_UPDATE_SCHEDULED, SCHEDULE_FAILED, UNREGISTERED}

    private State state;

    @Mock
    private WebTimelineFactory webTimelineFactory;

    @InjectMocks
    private TimelineService timelineService;

    private WebTimeline webTimeline;
    private Timeline<GitItem> timeline;
    private WebAutoUpdate autoUpdate;

    private WebSocketStompClient stompClient;

    private DefaultSessionHandler sessionHandler;


    @Before
    public void setUp() {
        this.mockWebTimeline();
        when(timeline.getItems()).thenReturn(newItems);
        when(timeline.getNewCount()).thenReturn(newCount);
        when(webTimelineFactory.create(SESSION_ID, URI, PROJECT_NAME)).thenReturn(webTimeline);

        RegisterResponse response = timelineService.register(SESSION_ID, URI, PROJECT_NAME);
        Assert.assertEquals(response.getId(), SESSION_ID);
        Assert.assertTrue(TimelineService.isValid(SESSION_ID));

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);

        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    private void mockWebTimeline() {
        timeline = mock(Timeline.class);
        autoUpdate = spy(new WebAutoUpdate(SESSION_ID, timeline));
        webTimeline = spy(new WebTimeline(SESSION_ID, timeline, autoUpdate));
    }

    public boolean startAutoUpdateGuard() {
        return state == State.START;
    }
    @Action
    public void startAutoUpdate() {
        sessionHandler = new DefaultSessionHandler();

        StompHeaders headers = new StompHeaders();
        headers.set("sessionId", SESSION_ID);
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        stompClient.connect(WEBSOCKET_URI, handshakeHeaders, headers, sessionHandler, port);

        state = State.AUTO_UPDATE_SCHEDULED;

    }

    public boolean startAutoUpdateWithUnknownSessionGuard() {
        return state == State.START;
    }
    @Action
    public void startAutoUpdateWithUnknownSession() {
        FailureSessionHandler sessionHandler = mock(FailureSessionHandler.class);

        StompHeaders headers = new StompHeaders();
        headers.set("sessionId", "UNKNOWN");
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        stompClient.connect(WEBSOCKET_URI, handshakeHeaders, headers, sessionHandler, port);

        verify(sessionHandler, never()).afterConnected(anyObject(), anyObject());
        state = State.SCHEDULE_FAILED;
    }


    public boolean updateGuard() {
        return state == State.AUTO_UPDATE_SCHEDULED;
    }
    @Action
    public void update() throws InterruptedException {
        Mockito.reset(timeline);
        Thread.sleep(5000);
        verify(timeline).getNewCount();
        verify(timeline).getItems();
    }


    public boolean disconnectGuard() {
        return state == State.AUTO_UPDATE_SCHEDULED;
    }
    @Action
    public void disconnect() throws InterruptedException {
        Assert.assertNotNull(sessionHandler);
        this.sessionHandler.disconnect();
        Thread.sleep(60 * 1000);
        state = State.UNREGISTERED;
    }

    class DefaultSessionHandler extends StompSessionHandlerAdapter {

        private StompSession session;

        @Override
        public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {
            session.subscribe(WEBSOCKET_RECEIVE_TOPIC, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return UpdateInfo.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    UpdateInfo info = (UpdateInfo) payload;
                    Assert.assertEquals(info.getNewCount(), newCount);
                    Assert.assertEquals(info.getItems().size(), newItems.size());
                }
            });
            session.send(WEBSOCKET_SEND_TOPIC, "");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // swallow
            }
            verify(timelineService).startAutoUpdate(SESSION_ID);
            verify(webTimeline).startAutoFresh();
            verify(autoUpdate).start();

            this.session = session;

            state = State.AUTO_UPDATE_SCHEDULED;
        }

        public void disconnect() {
            if (this.session != null) {
                this.session.disconnect();
            }
        }
    }


    class FailureSessionHandler extends StompSessionHandlerAdapter {
        private Gson GSON = new Gson();

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("connected!");
        }
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println(GSON.toJson(payload));
        }
    }

    @Test
    public void runTest() {
        WebFsmTestRunner.runTest(this, "timeline-websocket.dot");
    }
}
