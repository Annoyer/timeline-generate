package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.core.model.Timeline;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.util.Assertion;

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
		Assertion.check(timeline != null, "TIMELINE_MUST_NOT_BE_NULL");
		Assertion.check(itemUiFactory != null, "ITEM_UI_FACTORY_MUST_NOT_BE_NULL");

		ItemUiMap<I, Composite> itemUiMap = new ItemUiMap<>(timeline, itemUiFactory);
		itemUiList = new SwtItemUiList<>(itemUiMap);
		scroller = new SwtTopItemScroller<>(timeline, itemUiMap, itemUiList);
		topItemUpdater = new SwtTopItemUpdater<>(timeline, itemUiMap, itemUiList);
	}


	public ItemUiList<I, Composite> getItemUiList() {
		return itemUiList;
	}

	public TopItemScroller<I> getScroller() {
		return scroller;
	}

	public TopItemUpdater<I, Composite> getTopItemUpdater() {
		return topItemUpdater;
	}

}