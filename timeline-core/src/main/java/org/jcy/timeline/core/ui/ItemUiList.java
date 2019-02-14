package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.BackgroundProcessor;

public abstract class ItemUiList<I extends Item, U> {

	protected final BackgroundProcessor backgroundProcessor;
	protected final ItemUiMap<I, U> itemUiMap;

	/**
	 *
	 * @param itemUiMap
	 * @param backgroundProcessor
	 */
	protected ItemUiList(ItemUiMap<I, U> itemUiMap, BackgroundProcessor backgroundProcessor) {
		// TODO - implement ItemUiList.ItemUiList
		throw new UnsupportedOperationException();
	}

	public boolean isTimelineEmpty() {
		// TODO - implement ItemUiList.isTimelineEmpty
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param operation
	 */
	public void fetchInBackground(FetchOperation operation) {
		// TODO - implement ItemUiList.fetchInBackground
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param operation
	 */
	public void fetch(FetchOperation operation) {
		// TODO - implement ItemUiList.fetch
		throw new UnsupportedOperationException();
	}

	public void update() {
		// TODO - implement ItemUiList.update
		throw new UnsupportedOperationException();
	}

	protected abstract void beforeContentUpdate();

	protected abstract void afterContentUpdate();

	/**
	 *
	 * @param parent
	 */
	protected abstract void createUi(U parent);

	protected abstract U getContent();

	protected abstract U getUiRoot();

}