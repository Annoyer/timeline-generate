package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.swing.SwingUIUpdateListener;
import org.jcy.timeline.swing.TestUtil;
import org.mockito.Mockito;

public class ItemViewerProxy<I extends Item, U> extends ItemViewer<I, U> {

    private SwingUIUpdateListener listener;

    private final ItemViewer<I, U> actual;

    private final ItemUiList<I, U> actualItemUiList;
    private final TopItemScroller<I> actualScroller;
    private final TopItemUpdater<I, U> actualTopItemUpdater;

    public ItemViewerProxy(ItemViewer<I, U> actual) {
        super(new NullItemViewerCompound());
        this.actual = actual;
        this.actualItemUiList = (ItemUiList<I, U>) TestUtil.findFieldValue("itemUiList", actual);
        this.actualScroller = (TopItemScroller<I>) TestUtil.findFieldValue("scroller", actual);
        this.actualTopItemUpdater = (TopItemUpdater<I, U>) TestUtil.findFieldValue("topItemUpdater", actual);
    }

    public void addUpdateListener(SwingUIUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void update() {
        if (listener == null || listener.canUpdate()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // swallow
            }
            actual.update();
            if (listener != null) {
                listener.updateCompleted();
            }
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
