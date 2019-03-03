package org.jcy.timeline.core.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.util.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.BackgroundThreadHelper.directBackgroundProcessor;
import static org.jcy.timeline.core.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.core.ui.FetchOperation.NEW;
import static org.mockito.Mockito.*;

public class ItemUiListFsm implements FsmModel {

    private ItemUiList<Item, Object> target;

    private ItemUiMap<Item, Object> itemUiMap;
    private BackgroundProcessor backgroundProcessor;

    private enum State {START, CREATED, BACKGROUNG_THREAD_DOWN, UI_DISPATCHER_DOWN};
    
    private State state;

    public boolean createGuard() {
        return state == State.START;
    }
    @Action
    public void create() {
        itemUiMap = mock(ItemUiMap.class);
        backgroundProcessor = directBackgroundProcessor();
        target = spy(new FakeItemUiList(itemUiMap, backgroundProcessor));

        Assert.assertNotNull(target);
        Assert.assertNotNull(backgroundProcessor);
        Assert.assertNotNull(itemUiMap);

        state = State.CREATED;
    }

    public boolean isTimelineEmptyGuard() {
        return state == State.CREATED;
    }
    @Action
    public void isTimelineEmpty() {
        Mockito.reset(itemUiMap);
        target.isTimelineEmpty();

        verify(itemUiMap).isTimelineEmpty();
    }

    public boolean fetchGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetch() {
        Mockito.reset(itemUiMap, target);
        target.fetch(NEW);

        InOrder order = inOrder(itemUiMap, target);
        order.verify(itemUiMap).fetch(NEW);
        order.verify(target).update();
    }

    public boolean fetchIfUiDispatcherDoesNotRunGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchIfUiDispatcherDoesNotRun() {
        Mockito.reset(itemUiMap, target);
        doNothing().when(backgroundProcessor).dispatchToUiThread(any(Runnable.class));

        target.fetchInBackground(NEW);

        verify(itemUiMap).fetch(NEW);
        verify(target, never()).update();

        state = State.UI_DISPATCHER_DOWN;
    }

    public boolean fetchWithNullAsOperationGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchWithNullAsOperation() {
        Throwable actual = thrownBy(() -> target.fetch(null));

        assertThat(actual)
                .hasMessage(Messages.get("OPERATION_MUST_NOT_BE_NULL"));
    }


    public boolean fetchInBackgroundGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchInBackground() {
        Mockito.reset(itemUiMap, target);
        target.fetchInBackground(NEW);

        InOrder order = inOrder(itemUiMap, target);
        order.verify(itemUiMap).fetch(NEW);
        order.verify(target).update();
    }

    public boolean fetchInBackgroundIfThreadDoesNotRunGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchInBackgroundIfThreadDoesNotRun() {
        Mockito.reset(itemUiMap, target);
        doNothing().when(backgroundProcessor).process(any(Runnable.class));

        target.fetchInBackground(NEW);

        verify(itemUiMap, never()).fetch(NEW);
        verify(target, never()).update();

        state = State.BACKGROUNG_THREAD_DOWN;
    }

    public boolean fetchInBackgroundIfUiDispatcherDoesNotRunGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchInBackgroundIfUiDispatcherDoesNotRun() {
        Mockito.reset(itemUiMap, target);
        doNothing().when(backgroundProcessor).dispatchToUiThread(any(Runnable.class));

        target.fetchInBackground(NEW);

        verify(itemUiMap).fetch(NEW);
        verify(target, never()).update();

        state = State.UI_DISPATCHER_DOWN;
    }

    public boolean fetchInBackgroundWithNullAsOperationGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchInBackgroundWithNullAsOperation() {
        Throwable actual = thrownBy(() -> target.fetchInBackground(null));

        assertThat(actual)
                .hasMessage(Messages.get("OPERATION_MUST_NOT_BE_NULL"));
    }

    public boolean updateGuard() {
        return state == State.CREATED;
    }
    @Action
    public void update() {
        Mockito.reset(itemUiMap, target);
        target.update();

        InOrder order = inOrder(itemUiMap, target);
        order.verify(target).beforeContentUpdate();
        order.verify(itemUiMap).update(target.getContent());
        order.verify(target).afterContentUpdate();
    }

    @Override
    public Object getState() {
        return state == State.CREATED;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }

    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "item-ui-list-fsm.dot");
    }


    static class FakeItemUiList extends ItemUiList<Item, Object> {

        private final Object content;

        FakeItemUiList(ItemUiMap<Item, Object> itemUiMap, BackgroundProcessor backgroundProcessor) {
            super(itemUiMap, backgroundProcessor);
            content = new Object();
        }

        @Override
        protected void createUi(Object parent) {
        }

        @Override
        protected void beforeContentUpdate() {
        }

        @Override
        protected void afterContentUpdate() {
        }

        @Override
        protected Object getContent() {
            return content;
        }

        @Override
        protected Object getUiRoot() {
            return null;
        }
    }
}
