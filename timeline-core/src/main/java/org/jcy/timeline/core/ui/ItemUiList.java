package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.BackgroundProcessor;

/**
 * The collection of the ui items.
 *
 * @param <I>
 * @param <U>
 */
public abstract class ItemUiList<I extends Item, U> {

	protected final BackgroundProcessor backgroundProcessor;
	protected final ItemUiMap<I, U> itemUiMap;

	/**
	 * Create a new ItemUIList.
	 *
	 * @param itemUiMap
	 * @param backgroundProcessor
	 */
	protected ItemUiList(ItemUiMap<I, U> itemUiMap, BackgroundProcessor backgroundProcessor) {
		this.backgroundProcessor = backgroundProcessor;
		this.itemUiMap = itemUiMap;
	}

	public boolean isTimelineEmpty() {
		return itemUiMap.isTimelineEmpty();
	}

	/**
	 * Fetch commits in background.
	 *
	 * @param operation fetch operation.
	 */
	public void fetchInBackground(FetchOperation operation) {
		Assertion.check(operation != null, "OPERATION_MUST_NOT_BE_NULL");

		backgroundProcessor.process(() -> fetch(operation));
	}

	/**
	 * Fetch commits with current thread.
	 *
	 * @param operation fetch operation.
	 */
	public void fetch(FetchOperation operation) {
		Assertion.check(operation != null, "OPERATION_MUST_NOT_BE_NULL");

		itemUiMap.fetch(operation);
		backgroundProcessor.dispatchToUiThread(this::update);
	}

	/**
	 * Update the timeline content.
	 */
	public void update() {
		beforeContentUpdate();
		itemUiMap.update(getContent());
		afterContentUpdate();
	}

	protected abstract void beforeContentUpdate();

	protected abstract void afterContentUpdate();

	protected abstract void createUi(U parent);

	protected abstract U getContent();

	protected abstract U getUiRoot();

}