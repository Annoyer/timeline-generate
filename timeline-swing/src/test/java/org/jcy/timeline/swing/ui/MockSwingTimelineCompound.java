package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.swing.GitTimelineFactoryTest;
import org.jcy.timeline.swing.ItemViewerProxy;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;

public class MockSwingTimelineCompound extends SwingTimelineCompound<GitItem> {

    private ItemViewerProxy<GitItem, Container> proxyItemViewer;

    private Header<GitItem> spyHeader;

    private SwingItemViewerCompound<GitItem> actualViewerCompound;

    public MockSwingTimelineCompound(ItemProvider<GitItem> itemProvider, ItemUiFactory<GitItem, Container> uiFactory, SessionStorage<GitItem> sessionStorage) {
        super(itemProvider, uiFactory, sessionStorage);
    }

    @Override
    SwingItemViewerCompound<GitItem> createItemViewerCompound(Timeline<GitItem> timeline, ItemUiFactory<GitItem, Container> itemUiFactory) {
        actualViewerCompound = super.createItemViewerCompound(timeline, itemUiFactory);
        return new MockSwingItemViewerCompound<>(timeline, itemUiFactory);
    }

    @Override
    public ItemViewer<GitItem, Container> getItemViewer() {
        if (proxyItemViewer == null) {
            proxyItemViewer = Mockito.spy(new ItemViewerProxy<>(super.getItemViewer(), GitTimelineFactoryTest.getInstance()));
        }
        return proxyItemViewer;
    }

    public ItemViewer<GitItem, Container> spyItemViewer() {
        Mockito.reset(this.getItemViewer());
        return proxyItemViewer;
    }

    @Override
    public Header<GitItem> getHeader() {
        if (spyHeader == null) {
            spyHeader = Mockito.spy(super.getHeader());
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

        private SwingItemUiList<I> spyItemUiList;
        private SwingTopItemUpdater<I> spyTopItemUpdater;
        private SwingTopItemScroller<I> spyScroller;

        MockSwingItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Container> itemUiFactory) {
            super(timeline, itemUiFactory);
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
