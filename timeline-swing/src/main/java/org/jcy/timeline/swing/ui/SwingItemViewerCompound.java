package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.*;

import java.awt.Container;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.util.Assertion;

class SwingItemViewerCompound<I extends Item> implements ItemViewerCompound<I, Container> {

	private final SwingItemUiList<I> itemUiList;
	private final SwingTopItemUpdater<I> topItemUpdater;
	private final SwingTopItemScroller<I> scroller;

	/**
	 * Build the main data structures about the ui item list used in Swing UI
	 * with {@param timeline} and {@param itemUiFactory}.
	 *
	 * @param timeline timeline
	 * @param itemUiFactory itemUiFactory
	 */
	SwingItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Container> itemUiFactory) {
		Assertion.check(timeline != null, "TIMELINE_MUST_NOT_BE_NULL");
		Assertion.check(itemUiFactory != null, "ITEM_UI_FACTORY_MUST_NOT_BE_NULL");

		ItemUiMap<I, Container> itemUiMap = new ItemUiMap<>(timeline, itemUiFactory);
		itemUiList = new SwingItemUiList<>(itemUiMap);
		scroller = new SwingTopItemScroller<>(timeline, itemUiMap, (SwingItemUiList<I>) this.getItemUiList());
		topItemUpdater = new SwingTopItemUpdater<>(timeline, itemUiMap, (SwingItemUiList<I>) this.getItemUiList());
	}

	public ItemUiList<I, Container> getItemUiList() {
		return itemUiList;
	}

	public TopItemScroller<I> getScroller() {
		return scroller;
	}

	public TopItemUpdater<I, Container> getTopItemUpdater() {
		return topItemUpdater;
	}

}