package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewerCompound;
import java.awt.Container;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.ui.ItemUiList;
import org.jcy.timeline.core.ui.TopItemScroller;
import org.jcy.timeline.core.ui.TopItemUpdater;

class SwingItemViewerCompound<I extends Item> implements ItemViewerCompound<I, Container> {

	private final SwingItemUiList<I> itemUiList;
	private final SwingTopItemUpdater<I> topItemUpdater;
	private final SwingTopItemScroller<I> scroller;

	/**
	 *
	 * @param timeline
	 * @param itemUiFactory
	 */
	SwingItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Container> itemUiFactory) {
		// TODO - implement SwingItemViewerCompound.SwingItemViewerCompound
		throw new UnsupportedOperationException();
	}

	public ItemUiList<I, Container> getItemUiList() {
		// TODO - implement SwingItemViewerCompound.getItemUiList
		throw new UnsupportedOperationException();
	}

	public TopItemScroller<I> getScroller() {
		// TODO - implement SwingItemViewerCompound.getScroller
		throw new UnsupportedOperationException();
	}

	public TopItemUpdater<I, Container> getTopItemUpdater() {
		// TODO - implement SwingItemViewerCompound.getTopItemUpdater
		throw new UnsupportedOperationException();
	}

}