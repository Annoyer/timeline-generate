package org.jcy.timeline.swt.ui;

import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.eclipse.swt.widgets.Display;

public class SwtUiThreadDispatcher implements UiThreadDispatcher {

	private final Display display;

	public SwtUiThreadDispatcher() {
		this(Display.getCurrent());
	}

	/**
	 *
	 * @param display
	 */
	public SwtUiThreadDispatcher(Display display) {
		Assertion.check(display != null, "DISPLAY_MUST_NOT_BE_NULL");

		this.display = display;
	}

	/**
	 *
	 * @param runnable
	 */
	public void dispatch(Runnable runnable) {
		Assertion.check(runnable != null, "RUNNABLE_MUST_NOT_BE_NULL");

		if (!display.isDisposed()) {
			display.asyncExec(runnable);
		}
	}

}