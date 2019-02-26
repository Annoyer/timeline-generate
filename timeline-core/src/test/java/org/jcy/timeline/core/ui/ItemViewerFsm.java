package org.jcy.timeline.core.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.FsmTestHelper;
import org.jcy.timeline.core.model.Item;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class ItemViewerFsm implements FsmModel {

    private static ItemViewerFsm INSTANCE = new ItemViewerFsm();

    private enum State {START, CREATED, UI_CREATED, INITIALIZED};

    private State state;

    private TopItemUpdater<Item, Object> stubTopItemUpdater = ItemViewerCompoundHelper.stubTopItemUpdater();

    private TopItemScroller<Item> stubScroller = ItemViewerCompoundHelper.stubScroller();

    private ItemUiList<Item, Object> stubItemList = ItemViewerCompoundHelper.stubItemList();

    private ItemViewer<Item, Object> target;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
        target = null;
        Mockito.reset(stubTopItemUpdater, stubScroller, stubItemList);
    }

    public boolean createdItemViewerGuard() {
        return state == State.START;
    }
    @Action
    public void createdItemViewer() {
        ItemViewerCompound<Item, Object> compound = ItemViewerCompoundHelper
                .stubItemViewerCompound(stubItemList, stubScroller, stubTopItemUpdater);
        target = new ItemViewer<>(compound);

        Assert.assertNotNull(target);
        Assert.assertEquals(stubItemList, compound.getItemUiList());
        Assert.assertEquals(stubScroller, compound.getScroller());
        Assert.assertEquals(stubTopItemUpdater, compound.getTopItemUpdater());
        state = State.CREATED;
    }

    public boolean createUiGuard() {
        return state == State.CREATED;
    }
    @Action
    public void createUi() {
        Object parent = new Object();
        target.createUi(parent);
        verify(stubItemList).createUi(parent);

        state = State.UI_CREATED;
    }

    public boolean getUiRootGuard() {
        return state == State.UI_CREATED;
    }
    @Action
    public void getUiRoot() {
        Mockito.reset(stubItemList);
        target.getUiRoot();
        verify(stubItemList).getUiRoot();
    }

    public boolean initializeGuard() {
        return state == State.UI_CREATED && System.currentTimeMillis() % 2 == 0;
    }
    @Action
    public void initialize() {
        when(stubItemList.isTimelineEmpty()).thenReturn(false);
        target.initialize();
        verify(stubItemList, never()).fetch(FetchOperation.MORE);
        verify(stubItemList).update();
        verify(stubScroller).scrollIntoView();
        verify(stubTopItemUpdater).register();

        state = State.INITIALIZED;
    }

    public boolean initializeWithFetchMoreGuard() {
        return state == State.UI_CREATED && System.currentTimeMillis() % 2 == 1;
    }
    @Action
    public void initializeWithFetchMore() {
        when(stubItemList.isTimelineEmpty()).thenReturn(true);
        target.initialize();
        verify(stubItemList).fetch(FetchOperation.MORE);
        verify(stubItemList).update();
        verify(stubScroller).scrollIntoView();
        verify(stubTopItemUpdater).register();

        state = State.INITIALIZED;
    }

    public boolean fetchNewGuard() {
        return state == State.INITIALIZED;
    }
    @Action
    public void fetchNew() {
        Mockito.reset(stubItemList);
        target.fetchNew();
        verify(stubItemList).fetch(FetchOperation.NEW);
    }

    public boolean updateGuard() {
        return state == State.INITIALIZED;
    }
    @Action
    public void update() {
        Mockito.reset(stubItemList);
        target.update();
        verify(stubItemList).update();
    }

    @Test
    public void runTest() {
        FsmTestHelper.runTest(INSTANCE, "/item-viewer-fsm.dot");
    }
}
