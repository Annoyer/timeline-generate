package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The updater to change the top item in the current timeline.
 *
 * @param <I>
 * @param <U>
 */
public abstract class TopItemUpdater<I extends Item, U> {

	protected final ItemUiMap<I, U> itemUiMap;
	protected final Timeline<I> timeline;

	/**
	 * Create a new TopItemUpdater.
	 *
	 * @param timeline timeline
	 * @param itemUiMap itemUiMap
	 */
	protected TopItemUpdater(Timeline<I> timeline, ItemUiMap<I, U> itemUiMap) {
		this.timeline = timeline;
		this.itemUiMap = itemUiMap;
	}

	/**
	 * Update the top item.
	 */
	public void update() {
		List<I> items = calculateItemsBelowTop();
		if (!items.isEmpty()) {
			updateTopItem(items.get(0));
		}
	}

	protected abstract void register();

	/**
	 * Check if {@param itemUI} is currently show on screen.
	 *
	 * @param itemUi the UI item.
	 */
	protected abstract boolean isBelowTop(ItemUi<I> itemUi);

	/**
	 * Get the commit items which are currently show on screen.
	 *
	 * @return the commit items currently show on screen.
	 */
	private List<I> calculateItemsBelowTop() {
		return timeline.getItems()
				.stream()
				.filter(item -> isBelowTop(item))
				.collect(Collectors.toList());
	}

	/**
	 * Check if {@param item} is currently show on screen.
	 *
	 * @param item the commit item.
	 */
	private boolean isBelowTop(I item) {
		if (getItemUi(item) == null) {
			return false;
		}
		return isBelowTop(getItemUi(item));
	}

	/**
	 * Get the ui item from {@link TopItemUpdater#itemUiMap}
	 * with the specific commit item {@param item}.
	 *
	 * @param item commit item.
	 */
	private ItemUi<I> getItemUi(I item) {
		return itemUiMap.findByItemId(item.getId());
	}

	/**
	 * Change the top item in the timeline.
	 *
	 * @param newTopItem new top item.
	 */
	private void updateTopItem(I newTopItem) {
		Optional<I> oldTopItem = timeline.getTopItem();
		if (mustUpdate(newTopItem, oldTopItem)) {
			timeline.setTopItem(newTopItem);
		}
	}

	/**
	 * Check if the {@param newTopItem} is different from the {@param oldTopItem}
	 *
	 * @param newTopItem the new top item.
	 * @param oldTopItem the current top item on the screen.
	 */
	private boolean mustUpdate(I newTopItem, Optional<I> oldTopItem) {
		// optimize: simplify the bool expression.
		return !oldTopItem.isPresent() || !oldTopItem.get().equals(newTopItem);
	}

}