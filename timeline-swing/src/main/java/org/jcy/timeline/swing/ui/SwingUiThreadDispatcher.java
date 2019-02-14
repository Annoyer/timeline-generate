package org.jcy.timeline.swing.ui;

import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.UiThreadDispatcher;

import javax.swing.SwingUtilities;

public class SwingUiThreadDispatcher implements UiThreadDispatcher {

	/**
	 * Dispatch the task to the ui thread.
	 *
	 * @param runnable task
	 */
	public void dispatch(Runnable runnable) {
		Assertion.check(runnable != null, "RUNNABLE_MUST_NOT_BE_NULL");

		SwingUtilities.invokeLater(runnable);
	}

}