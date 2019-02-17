package org.jcy.timeline.core.ui;


/**
 * Auto update the ui.
 *
 * Optimize: Remove useless template types.
 *
 */
public interface AutoUpdate {

	/**
	 * Start auto update.
	 */
	void start();

	/**
	 * Stop auto update.
	 */
	void stop();

}