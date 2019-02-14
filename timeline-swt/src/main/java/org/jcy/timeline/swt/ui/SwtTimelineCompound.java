package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;

class SwtTimelineCompound<I extends Item> {

	private final ItemViewer<I, Composite> itemViewer;
	private final AutoUpdate autoUpdate;
	private final Header<I> header;

	static BackgroundProcessor createBackgroundProcessor() {
		// TODO - implement SwtTimelineCompound.createBackgroundProcessor
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	SwtTimelineCompound(ItemProvider<I> itemProvider, ItemUiFactory<I, Composite> itemUiFactory, SessionStorage<I> sessionStorage) {
		// TODO - implement SwtTimelineCompound.SwtTimelineCompound
		throw new UnsupportedOperationException();
	}

	ItemViewer<I, Composite> getItemViewer() {
		return this.itemViewer;
	}

	Header<I> getHeader() {
		return this.header;
	}

	AutoUpdate<I, Composite> getAutoUpdate() {
		// TODO - implement SwtTimelineCompound.getAutoUpdate
		throw new UnsupportedOperationException();
	}

}