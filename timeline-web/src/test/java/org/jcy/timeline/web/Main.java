package org.jcy.timeline.web;

import com.google.gson.Gson;
import org.jcy.timeline.web.websocket.TimelineWebsocketFsm;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {


    private static final String WEBSOCKET_URI = "ws://localhost:{port}/websocket";
    private static final String WEBSOCKET_SEND_TOPIC = "/msg/startAutoUpdate";
    private static final String WEBSOCKET_RECEIVE_TOPIC = "/user/unicast/autoUpdate";

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        new Main().connect();
    }


    private WebSocketStompClient stompClient;

    private StompSession session;

    private SockJsClient sockJsClient;

    private int port = 8080;


    public void connect() throws InterruptedException, ExecutionException, TimeoutException {

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        this.sockJsClient = new SockJsClient(transports);

        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompHeaders headers = new StompHeaders();
        headers.set("sessionId", "UNKNOWN");

        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        handshakeHeaders.set("sId", "UNKNOWN");
        session = stompClient
                .connect(WEBSOCKET_URI, handshakeHeaders, headers, new DefaultHandler(), port)
                .get(60, SECONDS);
    }

    class DefaultHandler extends StompSessionHandlerAdapter {
        private Gson GSON = new Gson();

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("connected!");

            session.send(WEBSOCKET_SEND_TOPIC, "");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // swallow
            }

            session.disconnect();
        }
        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println(GSON.toJson(payload));
        }
    }
}
