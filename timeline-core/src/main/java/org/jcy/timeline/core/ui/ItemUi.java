package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

/**
 * The UI item of the specific commit item.
 *
 * @param <I>
 */
public interface ItemUi<I extends Item> {

	/**
	 * Update the ui item.
	 */
	void update();

}