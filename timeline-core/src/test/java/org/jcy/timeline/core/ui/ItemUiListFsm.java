package org.jcy.timeline.core.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.FsmTestHelper;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.BackgroundProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.jcy.timeline.core.BackgroundThreadHelper.directBackgroundProcessor;
import static org.jcy.timeline.core.ui.FetchOperation.NEW;
import static org.mockito.Mockito.*;

public class ItemUiListFsm implements FsmModel {

    private static ItemUiListFsm INSTANCE = new ItemUiListFsm();

    private ItemUiList<Item, Object> target;

    private ItemUiMap<Item, Object> itemUiMap;
    private BackgroundProcessor backgroundProcessor;

    private boolean STATE_IS_CREATED;

    public boolean createGuard() {
        return !STATE_IS_CREATED;
    }
    @Action
    public void create() {
        itemUiMap = mock(ItemUiMap.class);
        backgroundProcessor = directBackgroundProcessor();
        target = spy(new FakeItemUiList(itemUiMap, backgroundProcessor));

        Assert.assertNotNull(target);
        Assert.assertNotNull(backgroundProcessor);
        Assert.assertNotNull(itemUiMap);

        STATE_IS_CREATED = true;
    }

    public boolean isTimelineEmptyGuard() {
        return STATE_IS_CREATED;
    }
    @Action
    public void isTimelineEmpty() {
        Mockito.reset(itemUiMap);
        target.isTimelineEmpty();

        verify(itemUiMap).isTimelineEmpty();
    }

    public boolean fetchGuard() {
        return STATE_IS_CREATED;
    }
    @Action
    public void fetch() {
        Mockito.reset(itemUiMap, target);
        target.fetch(NEW);

        InOrder order = inOrder(itemUiMap, target);
        order.verify(itemUiMap).fetch(NEW);
        order.verify(target).update();
    }


    public boolean fetchInBackgroundGuard() {
        return STATE_IS_CREATED;
    }
    @Action
    public void fetchInBackground() {
        Mockito.reset(itemUiMap, target);
        target.fetchInBackground(NEW);

        InOrder order = inOrder(itemUiMap, target);
        order.verify(itemUiMap).fetch(NEW);
        order.verify(target).update();
    }

    public boolean updateGuard() {
        return STATE_IS_CREATED;
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
        return STATE_IS_CREATED;
    }

    @Override
    public void reset(boolean testing) {
        STATE_IS_CREATED = false;
    }

    @Test
    public void runTest() {
        FsmTestHelper.runTest(INSTANCE, "item-ui-list-fsm.dot");
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
