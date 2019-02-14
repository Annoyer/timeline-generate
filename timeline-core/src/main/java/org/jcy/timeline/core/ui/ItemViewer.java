package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

public class ItemViewer<I extends Item, U> {

	private final ItemUiList<I, U> itemUiList;
	private final TopItemScroller<I> scroller;
	private final TopItemUpdater<I, U> topItemUpdater;

	/**
	 *
	 * @param itemViewerCompound
	 */
	public ItemViewer(ItemViewerCompound<I, U> itemViewerCompound) {
		// TODO - implement ItemViewer.ItemViewer
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	public void createUi(U parent) {
		// TODO - implement ItemViewer.createUi
		throw new UnsupportedOperationException();
	}

	public U getUiRoot() {
		// TODO - implement ItemViewer.getUiRoot
		throw new UnsupportedOperationException();
	}

	public void initialize() {
		// TODO - implement ItemViewer.initialize
		throw new UnsupportedOperationException();
	}

	public void fetchNew() {
		// TODO - implement ItemViewer.fetchNew
		throw new UnsupportedOperationException();
	}

	public void update() {
		// TODO - implement ItemViewer.update
		throw new UnsupportedOperationException();
	}

}