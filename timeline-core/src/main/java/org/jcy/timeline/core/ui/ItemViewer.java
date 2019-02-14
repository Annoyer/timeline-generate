package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

public class ItemViewer<I extends Item, U> {

	private final ItemUiList<I, U> itemUiList;
	private final TopItemScroller<I> scroller;
	private final TopItemUpdater<I, U> topItemUpdater;

	/**
	 * Create a new ItemViewer with {@param itemViewerCompound}.
	 * The {@param itemViewerCompound} consists of
	 * 		an {@link ItemUiList},
	 * 		a {@link TopItemScroller}
	 * 		and a {@link TopItemUpdater}.
	 *
	 * @param itemViewerCompound item viewer compound.
	 */
	public ItemViewer(ItemViewerCompound<I, U> itemViewerCompound) {
		itemUiList = itemViewerCompound.getItemUiList();
		scroller = itemViewerCompound.getScroller();
		topItemUpdater = itemViewerCompound.getTopItemUpdater();
	}

	/**
	 * todo
	 * @param parent
	 */
	public void createUi(U parent) {
		itemUiList.createUi(parent);
	}

	/**
	 * todo
	 * @return
	 */
	public U getUiRoot() {
		return itemUiList.getUiRoot();
	}

	/**
	 * Initialize the item viewer.
	 */
	public void initialize() {
		if (itemUiList.isTimelineEmpty()) {
			itemUiList.fetch(FetchOperation.MORE);
		}
		itemUiList.update();
		scroller.scrollIntoView();
		topItemUpdater.register();
	}

	/**
	 * Fetch new.
	 */
	public void fetchNew() {
		itemUiList.fetch(FetchOperation.NEW);
	}

	/**
	 * Update the item ui list.
	 */
	public void update() {
		itemUiList.update();
	}

}