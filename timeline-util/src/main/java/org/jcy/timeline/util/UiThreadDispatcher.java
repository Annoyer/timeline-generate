package org.jcy.timeline.util;

/**
 * UI thread dispatcher.
 */
public interface UiThreadDispatcher {

	/**
	 * Dispatch the task to the ui thread.
	 *
	 * @param runnable task.
	 */
	void dispatch(Runnable runnable);

}