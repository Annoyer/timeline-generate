package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import java.util.List;
import java.util.Optional;

public abstract class TopItemUpdater<I extends Item, U> {

	protected final ItemUiMap<I, U> itemUiMap;
	protected final Timeline<I> timeline;

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 */
	protected TopItemUpdater(Timeline<I> timeline, ItemUiMap<I, U> itemUiMap) {
		// TODO - implement TopItemUpdater.TopItemUpdater
		throw new UnsupportedOperationException();
	}

	public void update() {
		// TODO - implement TopItemUpdater.update
		throw new UnsupportedOperationException();
	}

	protected abstract void register();

	/**
	 *
	 * @param itemUi
	 */
	protected abstract boolean isBelowTop(ItemUi<I> itemUi);

	private List<I> calculateItemsBelowTop() {
		// TODO - implement TopItemUpdater.calculateItemsBelowTop
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param item
	 */
	private boolean isBelowTop(I item) {
		// TODO - implement TopItemUpdater.isBelowTop
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param item
	 */
	private ItemUi<I> getItemUi(I item) {
		// TODO - implement TopItemUpdater.getItemUi
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param newTopItem
	 */
	private void updateTopItem(I newTopItem) {
		// TODO - implement TopItemUpdater.updateTopItem
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param newTopItem
	 * @param oldTopItem
	 */
	private boolean mustUpdate(I newTopItem, Optional<I> oldTopItem) {
		// TODO - implement TopItemUpdater.mustUpdate
		throw new UnsupportedOperationException();
	}

}