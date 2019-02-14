package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewerCompound;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.ItemUiList;
import org.jcy.timeline.core.ui.TopItemScroller;
import org.jcy.timeline.core.ui.TopItemUpdater;

class SwtItemViewerCompound<I extends Item> implements ItemViewerCompound {

	private final SwtTopItemUpdater<I> topItemUpdater;
	private final SwtTopItemScroller<I> scroller;
	private final SwtItemUiList<I> itemUiList;

	/**
	 *
	 * @param timeline
	 * @param itemUiFactory
	 */
	SwtItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Composite> itemUiFactory) {
		// TODO - implement SwtItemViewerCompound.SwtItemViewerCompound
		throw new UnsupportedOperationException();
	}

	public ItemUiList<I, Composite> getItemUiList() {
		// TODO - implement SwtItemViewerCompound.getItemUiList
		throw new UnsupportedOperationException();
	}

	public TopItemScroller<I> getScroller() {
		// TODO - implement SwtItemViewerCompound.getScroller
		throw new UnsupportedOperationException();
	}

	public TopItemUpdater<I, Composite> getTopItemUpdater() {
		// TODO - implement SwtItemViewerCompound.getTopItemUpdater
		throw new UnsupportedOperationException();
	}

}