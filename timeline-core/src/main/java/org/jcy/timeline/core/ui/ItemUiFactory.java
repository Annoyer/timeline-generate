package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

/**
 * UI Item Factory.
 *
 * @param <I>
 * @param <U>
 */
public interface ItemUiFactory<I extends Item, U> {

	/**
	 * Create an ui item with the specific commit item in the ui context component.
	 *
	 * @param uiContext the ui context component.
	 * @param item commit item.
	 * @param index the position in the context component's list at which to insert
	 */
	ItemUi<I> create(U uiContext, I item, int index);

}