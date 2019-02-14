package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jcy.timeline.core.ui.ItemViewer;
import java.awt.Container;

public class SwingAutoUpdate<I extends Item> {

	private final ActionListener itemViewerNotifier;
	private final ActionListener headerNofifier;
	private final Timer timer;

	/**
	 *
	 * @param header
	 * @param itemViewer
	 * @param delay
	 */
	SwingAutoUpdate(Header<I> header, ItemViewer<I, Container> itemViewer, int delay) {
		// TODO - implement SwingAutoUpdate.SwingAutoUpdate
		throw new UnsupportedOperationException();
	}

}