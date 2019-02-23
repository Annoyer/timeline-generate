package org.jcy.timeline.swing;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.*;
import org.mockito.Mockito;

import static org.jcy.timeline.swing.TimelineStateMachine.State.RUNNING_TIME_WAITING;
import static org.jcy.timeline.swing.TimelineStateMachine.State.RUNNING_UPDATING;

public class ItemViewerProxy<I extends Item, U> extends ItemViewer<I, U> {

    private final GitTimelineFactoryTest test;

    private final ItemViewer<I, U> actual;

    private final ItemUiList<I, U> actualItemUiList;
    private final TopItemScroller<I> actualScroller;
    private final TopItemUpdater<I, U> actualTopItemUpdater;

    public ItemViewerProxy(ItemViewer<I, U> actual, GitTimelineFactoryTest test) {
        super(new NullItemViewerCompound());
        this.actual = actual;
        this.test = test;
        this.actualItemUiList = (ItemUiList<I, U>) TestUtil.findFieldValue("itemUiList", actual);
        this.actualScroller = (TopItemScroller<I>) TestUtil.findFieldValue("scroller", actual);
        this.actualTopItemUpdater = (TopItemUpdater<I, U>) TestUtil.findFieldValue("topItemUpdater", actual);
    }

    @Override
    public void update() {
        if (test.state.setIfMatch(RUNNING_UPDATING, RUNNING_TIME_WAITING)) {
            actual.update();
            test.state.setState(RUNNING_TIME_WAITING);
        }
    }

    /**
     * todo
     * @param parent
     */
    public void createUi(U parent) {
        actual.createUi(parent);
    }

    /**
     * todo
     * @return
     */
    public U getUiRoot() {
        return actual.getUiRoot();
    }

    /**
     * Initialize the item viewer.
     */
    public void initialize() {
        actual.initialize();
    }

    @Override
    public void fetchNew() {
        actual.fetchNew();
    }

    public ItemUiList<I, U> getItemUiList() {
        return actualItemUiList;
    }
    public TopItemScroller<I> getScroller() {
        return actualScroller;
    }
    public TopItemUpdater<I, U> getTopItemUpdater() {
        return actualTopItemUpdater;
    }

    public ItemUiList<I, U> spyItemUiList() {
        Mockito.reset(actualItemUiList);
        return actualItemUiList;
    }
    public TopItemScroller<I> spyScroller() {
        Mockito.reset(actualScroller);
        return actualScroller;
    }
    public TopItemUpdater<I, U> spyTopItemUpdater() {
        Mockito.reset(actualTopItemUpdater);
        return actualTopItemUpdater;
    }


    private static class NullItemViewerCompound<I extends Item, U> implements ItemViewerCompound<I, U> {

        @Override
        public ItemUiList<I, U> getItemUiList() {
            return null;
        }

        @Override
        public TopItemScroller<I> getScroller() {
            return null;
        }

        @Override
        public TopItemUpdater<I, U> getTopItemUpdater() {
            return null;
        }
    }
}