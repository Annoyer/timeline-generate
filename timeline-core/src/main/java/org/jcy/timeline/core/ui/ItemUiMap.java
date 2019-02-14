package org.jcy.timeline.core.ui;

import java.util.Map;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;

public class ItemUiMap<I extends Item, U> {

	protected final ItemUiFactory itemUiFactory;
	protected final Map<String, ItemUi<I>> itemUis;
	private Timeline<I> timeline;

	/**
	 *
	 * @param timeline
	 * @param itemUiFactory
	 */
	public ItemUiMap(Timeline<I> timeline, ItemUiFactory<I, U> itemUiFactory) {
		// TODO - implement ItemUiMap.ItemUiMap
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param id
	 */
	public boolean containsItemUi(String id) {
		// TODO - implement ItemUiMap.containsItemUi
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param id
	 */
	public ItemUi<I> findByItemId(String id) {
		// TODO - implement ItemUiMap.findByItemId
		throw new UnsupportedOperationException();
	}

	public boolean isTimelineEmpty() {
		// TODO - implement ItemUiMap.isTimelineEmpty
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param operation
	 */
	public void fetch(FetchOperation operation) {
		// TODO - implement ItemUiMap.fetch
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uiContext
	 */
	public void update(U uiContext) {
		// TODO - implement ItemUiMap.update
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uiContext
	 * @param item
	 */
	private void doUpdate(U uiContext, I item) {
		// TODO - implement ItemUiMap.doUpdate
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uiContext
	 * @param item
	 */
	private void createItem(U uiContext, I item) {
		// TODO - implement ItemUiMap.createItem
		throw new UnsupportedOperationException();
	}

}