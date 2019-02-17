package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.jcy.timeline.util.Assertion;

import org.jcy.timeline.core.ui.AutoUpdate;

class SwtAutoUpdate<I extends Item> implements AutoUpdate {

	private final ItemViewer<I, Composite> itemViewer;
	private final Header<I> header;
	private final Display display;
	private final int delay;
	private volatile boolean started;

	/**
	 *
	 * @param header
	 * @param itemViewer
	 * @param delay
	 */
	SwtAutoUpdate(Header<I> header, ItemViewer<I, Composite> itemViewer, int delay) {
		Assertion.check(header != null, "HEADER_MUST_NOT_BE_NULL");
		Assertion.check(itemViewer != null, "ITEM_VIEWER_MUST_NOT_BE_NULL");
		Assertion.check(delay >= 0, "DELAY_MUST_NOT_BE_NEGATIVE");

		this.display = Display.getCurrent();
		this.itemViewer = itemViewer;
		this.header = header;
		this.delay = delay;
	}

	private void triggerUpdate() {
		if (started) {
			itemViewer.update();
			header.update();
			start();
		}
	}

	/**
	 * Start auto update.
	 */
	public void start() {
		started = true;
		display.timerExec(delay, () -> triggerUpdate());
	}

	/**
	 * Stop auto update.
	 */
	public void stop() {
		started=false;
	}

}