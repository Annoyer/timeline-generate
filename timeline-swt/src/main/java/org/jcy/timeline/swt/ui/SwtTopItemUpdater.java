package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemUpdater;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.eclipse.swt.widgets.Control;
import org.jcy.timeline.core.ui.ItemUi;

public class SwtTopItemUpdater<I extends Item> extends TopItemUpdater<I, Composite> {

	static final int TOP_OFF_SET = 5;
	private final UiThreadDispatcher uiThreadDispatcher;
	private final SwtItemUiList<I> itemUiList;

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 */
	SwtTopItemUpdater(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList) {
		// TODO - implement SwtTopItemUpdater.SwtTopItemUpdater
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 * @param dispatcher
	 */
	SwtTopItemUpdater(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		// TODO - implement SwtTopItemUpdater.SwtTopItemUpdater
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param control
	 */
	private boolean isBelowTop(Control control) {
		// TODO - implement SwtTopItemUpdater.isBelowTop
		throw new UnsupportedOperationException();
	}

	protected void register() {
		// TODO - implement SwtTopItemUpdater.register
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemUi
	 */
	protected boolean isBelowTop(ItemUi<I> itemUi) {
		// TODO - implement SwtTopItemUpdater.isBelowTop
		throw new UnsupportedOperationException();
	}

}