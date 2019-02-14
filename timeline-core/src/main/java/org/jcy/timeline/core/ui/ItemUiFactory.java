package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

public interface ItemUiFactory<I extends Item, U> {

	/**
	 *
	 * @param uiContext
	 * @param item
	 * @param index
	 */
	ItemUi<I> create(U uiContext, I item, int index);

}