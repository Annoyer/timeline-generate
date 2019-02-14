package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

/**
 * Top Item Scroller.
 *
 * @param <I>
 */
public interface TopItemScroller<I extends Item> {

	/**
	 * Scroll the top item into the screen view.
	 */
	void scrollIntoView();

}