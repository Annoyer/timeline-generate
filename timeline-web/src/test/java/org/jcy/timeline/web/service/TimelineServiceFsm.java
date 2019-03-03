package org.jcy.timeline.web.service;

import com.google.common.collect.Lists;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.model.Memento;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.provider.git.GitItemProvider;
import org.jcy.timeline.core.util.FileSessionStorage;
import org.jcy.timeline.web.ItemFactory;
import org.jcy.timeline.web.WebFsmTestRunner;
import org.jcy.timeline.web.model.*;
import org.jcy.timeline.web.ui.WebAutoUpdate;
import org.jcy.timeline.web.ui.WebTimeline;
import org.jcy.timeline.web.ui.WebTimelineFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimelineServiceFsm implements FsmModel {

    private static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";
    private static final String PROJECT_NAME = "jenkins-web-test";

    private enum State {START, REGISTERED, UNREGISTERED}

    private State state;

    @Mock
    private WebTimelineFactory timelineFactory;
    @InjectMocks
    private TimelineService timelineService;

    private WebTimeline timeline;

    private List<GitItem> newItems;

    private List<GitItem> moreItems;

    private GitItemProvider itemProvider;

    private FileSessionStorage<GitItem> storage;

    private int currentSessionId = 0;

    private WebTimeline stubTimeline() {
        newItems = Lists.newArrayList(ItemFactory.createNewItems(100, 6));
        moreItems = Lists.newArrayList(ItemFactory.createNewItems(1000, 10));

        itemProvider = mock(GitItemProvider.class);
        when(itemProvider.fetchNew(any())).thenReturn(newItems);
        when(itemProvider.fetchItems(anyObject(), anyInt())).thenReturn(moreItems);

        storage = mock(FileSessionStorage.class);
        when(storage.read()).thenReturn(Memento.empty());
        String sId = String.valueOf(++currentSessionId);
        Timeline<GitItem> t = new Timeline<>(itemProvider, storage);
        WebAutoUpdate autoUpdate = new WebAutoUpdate(sId, t);
        timeline = spy(new WebTimeline(sId, t, autoUpdate));
        return timeline;
    }
    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }

    public boolean registerGuard() {
        return state == State.START;
    }
    @Action
    public void register() {
        WebTimeline expect = this.stubTimeline();
        when(timelineFactory.create(any(), any(), any())).thenReturn(expect);

        String sessionId = String.valueOf(currentSessionId);
        RegisterResponse response = timelineService.register(sessionId, URI, PROJECT_NAME);
        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getId(), sessionId);
        Assert.assertEquals(response.getItems().size(), moreItems.size());
        Assert.assertTrue(TimelineService.isValid(sessionId));
        verify(storage).read();

        state = State.REGISTERED;
    }

    public boolean fetchMoreGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void fetchMore() {
        Mockito.reset(itemProvider);
        FetchResponse withUnknownSession = timelineService.fetchMore("???");
        Assert.assertFalse(withUnknownSession.isSuccess());
        Assert.assertEquals("The sessionId [???] is not registered!", withUnknownSession.getCause());
        Assert.assertNull(withUnknownSession.getItems());

        FetchResponse response = timelineService.fetchMore(String.valueOf(currentSessionId));
        Assert.assertEquals(response.getItems().size(), moreItems.size());

        verify(itemProvider).fetchItems(anyObject(), anyInt());
    }

    public boolean fetchMoreWithUnknownSessionGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void fetchMoreWithUnknownSession() {
        Mockito.reset(itemProvider);
        FetchResponse withUnknownSession = timelineService.fetchMore("???");
        Assert.assertFalse(withUnknownSession.isSuccess());
        Assert.assertEquals("The sessionId [???] is not registered!", withUnknownSession.getCause());
        Assert.assertNull(withUnknownSession.getItems());
    }

    public boolean fetchNewGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void fetchNew() {
        FetchResponse response = timelineService.fetchNew(String.valueOf(currentSessionId));
        Assert.assertEquals(response.getItems().size(), moreItems.size());

        verify(itemProvider).fetchNew(anyObject());
    }

    public boolean fetchNewWithUnknownSessionGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void fetchNewWithUnknownSession() {
        Mockito.reset(itemProvider);
        FetchResponse withUnknownSession = timelineService.fetchNew("???");
        Assert.assertFalse(withUnknownSession.isSuccess());
        Assert.assertEquals("The sessionId [???] is not registered!", withUnknownSession.getCause());
        Assert.assertNull(withUnknownSession.getItems());
    }

    public boolean unregisterGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void unregister() {

        boolean result = timelineService.unregister(String.valueOf(currentSessionId));
        Assert.assertTrue(result);
        verify(timeline).stopAutoFresh();

        state = State.UNREGISTERED;
    }

    public boolean unregisterWithUnknownSessionGuard() {
        return state == State.REGISTERED;
    }
    @Action
    public void unregisterWithUnknownSession() {
        boolean unknownSession = timelineService.unregister("???");
        Assert.assertFalse(unknownSession);
    }

    @Test
    public void runTest() {
        WebFsmTestRunner.runTest(this, "timeline-controller-fsm.dot");
    }
}
