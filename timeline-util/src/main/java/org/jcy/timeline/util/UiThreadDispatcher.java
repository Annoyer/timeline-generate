package org.jcy.timeline.util;

public interface UiThreadDispatcher {

	/**
	 *
	 * @param runnable
	 */
	void dispatch(Runnable runnable);

}