package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import java.awt.Container;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;

public class SwingTimelineCompound<I extends Item> {

	private final ItemViewer<I, Container> itemViewer;
	private final AutoUpdate<I, Container> autoUpdate;
	private final Header<I> header;

	static BackgroundProcessor createBackgroundProcessor() {
		// TODO - implement SwingTimelineCompound.createBackgroundProcessor
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	SwingTimelineCompound(ItemProvider<I> itemProvider, ItemUiFactory<I, Container> itemUiFactory, SessionStorage<I> sessionStorage) {
		// TODO - implement SwingTimelineCompound.SwingTimelineCompound
		throw new UnsupportedOperationException();
	}

	ItemViewer<I, Container> getItemViewer() {
		return this.itemViewer;
	}

	Header<I> getHeader() {
		return this.header;
	}

	AutoUpdate<I, Container> getAutoUpdate() {
		return this.autoUpdate;
	}

}