package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

class SwtAutoUpdate<I extends Item> {

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
		// TODO - implement SwtAutoUpdate.SwtAutoUpdate
		throw new UnsupportedOperationException();
	}

	private void triggerUpdate() {
		// TODO - implement SwtAutoUpdate.triggerUpdate
		throw new UnsupportedOperationException();
	}

}