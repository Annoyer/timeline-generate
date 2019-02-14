package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemUpdater;
import java.awt.Container;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import java.awt.Component;
import org.jcy.timeline.core.ui.ItemUi;

public class SwingTopItemUpdater<I extends Item> extends TopItemUpdater<I, Container> {

	private final UiThreadDispatcher uiThreadDispatcher;
	private final SwingItemUiList<I> itemUiList;

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 */
	SwingTopItemUpdater(Timeline timeline, ItemUiMap<I, Container> itemUiMap, SwingItemUiList<I> itemUiList) {
		// TODO - implement SwingTopItemUpdater.SwingTopItemUpdater
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 * @param dispatcher
	 */
	SwingTopItemUpdater(Timeline timeline, ItemUiMap<I, Container> itemUiMap, SwingItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		// TODO - implement SwingTopItemUpdater.SwingTopItemUpdater
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param component
	 */
	private boolean isBelowTop(Component component) {
		// TODO - implement SwingTopItemUpdater.isBelowTop
		throw new UnsupportedOperationException();
	}

	protected void register() {
		// TODO - implement SwingTopItemUpdater.register
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemUi
	 */
	protected boolean isBelowTop(ItemUi<I> itemUi) {
		// TODO - implement SwingTopItemUpdater.isBelowTop
		throw new UnsupportedOperationException();
	}

}