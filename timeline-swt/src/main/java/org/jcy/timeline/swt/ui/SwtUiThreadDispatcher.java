package org.jcy.timeline.swt.ui;

import org.jcy.timeline.util.UiThreadDispatcher;
import org.eclipse.swt.widgets.Display;

public class SwtUiThreadDispatcher implements UiThreadDispatcher {

	private final Display display;

	public SwtUiThreadDispatcher() {
		// TODO - implement SwtUiThreadDispatcher.SwtUiThreadDispatcher
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param display
	 */
	public SwtUiThreadDispatcher(Display display) {
		// TODO - implement SwtUiThreadDispatcher.SwtUiThreadDispatcher
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param runnable
	 */
	public void dispatch(Runnable runnable) {
		// TODO - implement SwtUiThreadDispatcher.dispatch
		throw new UnsupportedOperationException();
	}

}