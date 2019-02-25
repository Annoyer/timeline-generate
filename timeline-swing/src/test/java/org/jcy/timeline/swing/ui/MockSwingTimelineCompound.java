package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.swing.SwingUIUpdateListener;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;

public class MockSwingTimelineCompound extends SwingTimelineCompound<GitItem> {

    private ItemViewerProxy<GitItem, Container> proxyItemViewer;

    private HeaderProxy<GitItem> spyHeader;


    private SwingUIUpdateListener<GitItem, Container> updateListener;

    public MockSwingTimelineCompound(ItemProvider<GitItem> itemProvider, ItemUiFactory<GitItem, Container> uiFactory, SessionStorage<GitItem> sessionStorage) {
        super(itemProvider, uiFactory, sessionStorage);
    }

    @Override
    SwingItemViewerCompound<GitItem> createItemViewerCompound(Timeline<GitItem> timeline, ItemUiFactory<GitItem, Container> itemUiFactory) {
        return new MockSwingItemViewerCompound<>(timeline, itemUiFactory, super.createItemViewerCompound(timeline, itemUiFactory));
    }

    @Override
    public synchronized ItemViewer<GitItem, Container> getItemViewer() {
        if (proxyItemViewer == null) {
            proxyItemViewer = Mockito.spy(new ItemViewerProxy<>(super.getItemViewer()));
            if (updateListener == null) {
                updateListener = new SwingUIUpdateListener<>(2);
            }
            updateListener.register(proxyItemViewer);
        }
        return proxyItemViewer;
    }

    public ItemViewer<GitItem, Container> spyItemViewer() {
        Mockito.reset(this.getItemViewer());
        return proxyItemViewer;
    }

    @Override
    public synchronized Header<GitItem> getHeader() {
        if (spyHeader == null) {
            spyHeader = Mockito.spy(new HeaderProxy<>(super.getHeader()));
            if (updateListener == null) {
                updateListener = new SwingUIUpdateListener<>(2);
            }
            updateListener.register(spyHeader);
        }
        return spyHeader;
    }

    public Header<GitItem> spyHeader() {
        Mockito.reset(this.getHeader());
        return spyHeader;
    }

    public JButton getFetchNewButton() {
        return this.getHeader().fetchNew;
    }

    public JButton getFetchMoreButton() {
        return ((SwingItemUiList<GitItem>)this.proxyItemViewer.getItemUiList()).fetchMore;
    }

    public SwingItemUiList<GitItem> spyItemUiList() {
        return (SwingItemUiList<GitItem>) proxyItemViewer.spyItemUiList();
    }

    public SwingTopItemScroller<GitItem> spyScroller() {
        return (SwingTopItemScroller<GitItem>) proxyItemViewer.spyScroller();
    }

    public SwingTopItemUpdater<GitItem> spyTopItemUpdater() {
        return (SwingTopItemUpdater<GitItem>) proxyItemViewer.spyTopItemUpdater();
    }

    public SwingItemUiList<GitItem> getItemUiList() {
        return (SwingItemUiList<GitItem>) proxyItemViewer.getItemUiList();
    }

    public SwingTopItemScroller<GitItem> getScroller() {
        return (SwingTopItemScroller<GitItem>) proxyItemViewer.getScroller();
    }

    public SwingTopItemUpdater<GitItem> getTopItemUpdater() {
        return (SwingTopItemUpdater<GitItem>) proxyItemViewer.getTopItemUpdater();
    }

    public JScrollPane getScrollPane() {
        return this.getItemUiList().uiRoot;
    }

    private class MockSwingItemViewerCompound<I extends Item> extends SwingItemViewerCompound<I> {

        private SwingItemViewerCompound<I> actual;

        private SwingItemUiList<I> spyItemUiList;
        private SwingTopItemUpdater<I> spyTopItemUpdater;
        private SwingTopItemScroller<I> spyScroller;

        MockSwingItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Container> itemUiFactory, SwingItemViewerCompound<I> actual) {
            super(timeline, itemUiFactory);
            this.actual = actual;
        }

        @Override
        public ItemUiList<I, Container> getItemUiList() {
            if (spyItemUiList ==  null) {
                spyItemUiList = (SwingItemUiList<I>) Mockito.spy(super.getItemUiList());
            }
            return spyItemUiList;
        }

        @Override
        public TopItemScroller<I> getScroller() {
            if (spyScroller ==  null) {
                spyScroller = (SwingTopItemScroller<I>) Mockito.spy(super.getScroller());
            }
            return spyScroller;
        }

        @Override
        public TopItemUpdater<I, Container> getTopItemUpdater() {
            if (spyTopItemUpdater ==  null) {
                spyTopItemUpdater = (SwingTopItemUpdater<I>) Mockito.spy(super.getTopItemUpdater());
            }
            return spyTopItemUpdater;
        }
    }
}
