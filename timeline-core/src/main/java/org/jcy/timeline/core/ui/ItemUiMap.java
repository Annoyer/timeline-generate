package org.jcy.timeline.core.ui;

import java.util.HashMap;
import java.util.Map;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;

import org.jcy.timeline.util.Assertion;

/**
 * Holds the mapping relation of ui items and commit items.
 *
 * @param <I>
 * @param <U>
 */
public class ItemUiMap<I extends Item, U> {

	protected final ItemUiFactory itemUiFactory;
	protected final Map<String, ItemUi<I>> itemUis;

	protected final Timeline<I> timeline;

	/**
	 * Create a new ItemUiMap.
	 * 
	 * @param timeline current timeline.
	 * @param itemUiFactory itemUiFactory.
	 */
	public ItemUiMap(Timeline<I> timeline, ItemUiFactory<I, U> itemUiFactory) {
		Assertion.check(timeline != null, "TIMELINE_MUST_NOT_BE_NULL");
		Assertion.check(itemUiFactory != null, "ITEM_UI_FACTORY_MUST_NOT_BE_NULL");

		this.itemUiFactory = itemUiFactory;
		this.timeline = timeline;
		this.itemUis = new HashMap<>();
	}

	/**
	 * @param id commit id.
	 * @return whether there is a ui item matching the commit item id.
	 */
	public boolean containsItemUi(String id) {
		Assertion.check(id != null, "ID_MUST_NOT_BE_NULL");

		return itemUis.containsKey(id);
	}

	/**
	 * @param id commit id.
	 * @return the ui item matching the commit item id.
	 */
	public ItemUi<I> findByItemId(String id) {
		Assertion.check(id != null, "ID_MUST_NOT_BE_NULL");

		return itemUis.get(id);
	}

	/**
	 * @return if the item list in timeline is empty.
	 */
	public boolean isTimelineEmpty() {
		return timeline.getItems().isEmpty();
	}


	/**
	 * Fetch commit items.
	 * 
	 * @param operation the specific fetch operation.
	 */
	public void fetch(FetchOperation operation) {
		Assertion.check(operation != null, "OPERATION_MUST_NOT_BE_NULL");

		operation.fetch(timeline);
	}

	/**
	 * Update the specific UI Context Component with current {@link ItemUiMap#timeline}.
	 * 
	 * @param uiContext the specific UI Context Component
	 */
	public void update(U uiContext) {
		Assertion.check(uiContext != null, "UI_CONTEXT_MUST_NOT_BE_NULL");

		timeline.getItems().forEach(item -> doUpdate(uiContext, item));
	}

	/**
	 * Update the specific commit ui item in timeline.
	 * 
	 * @param uiContext UI Context Component.
	 * @param item target commit item.
	 */
	private void doUpdate(U uiContext, I item) {
		if (containsItemUi(item.getId())) {
			findByItemId(item.getId()).update();
		} else {
			createItem(uiContext, item);
		}
	}

	/**
	 * Create a commit UI item with the git commit {@param item}.
	 * And put the new UI item into the {@link ItemUiMap#itemUis}.
	 * 
	 * @param uiContext UI Context Component.
	 * @param item target commit item.
	 */
	private void createItem(U uiContext, I item) {
		int index = timeline.getItems().indexOf(item);
		ItemUi<I> itemUi = itemUiFactory.create(uiContext, item, index);
		itemUis.put(item.getId(), itemUi);
	}

}