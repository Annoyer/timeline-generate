package org.jcy.timeline.core.ui;

import org.jcy.timeline.core.model.Item;

/**
 * Auto update the ui.
 *
 * @param <I>
 * @param <U>
 */
public interface AutoUpdate<I extends Item, U> {

	/**
	 * Start auto update.
	 */
	void start();

	/**
	 * Stop auto update.
	 */
	void stop();

}