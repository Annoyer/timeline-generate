package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jcy.timeline.core.ui.ItemViewer;
import java.awt.Container;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.util.Assertion;

public class SwingAutoUpdate<I extends Item> implements AutoUpdate<I, Container> {

	private final ActionListener itemViewerNotifier;
	private final ActionListener headerNofifier;
	private final Timer timer;

	/**
	 * Auto Update the Swing ui.
	 *
	 * @param header application header
	 * @param itemViewer item viewer
	 * @param delay refresh interval
	 */
	SwingAutoUpdate(Header<I> header, ItemViewer<I, Container> itemViewer, int delay) {
		Assertion.check(header != null, "HEADER_MUST_NOT_BE_NULL");
		Assertion.check(itemViewer != null, "ITEM_VIEWER_MUST_NOT_BE_NULL");
		Assertion.check(delay >= 0, "DELAY_MUST_NOT_BE_NEGATIVE");

		this.timer = new Timer(delay, null);
		this.itemViewerNotifier = event -> itemViewer.update();
		this.headerNofifier = event -> header.update();
	}

	/**
	 * Start auto update.
	 */
	public void start() {
		timer.addActionListener(itemViewerNotifier);
		timer.addActionListener(headerNofifier);
		timer.start();
	}

	/**
	 * Stop auto update.
	 */
	public void stop() {
		timer.stop();
		timer.removeActionListener(headerNofifier);
		timer.removeActionListener(itemViewerNotifier);
	}

}