package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;

class SwtTimelineCompound<I extends Item> {

	private final ItemViewer<I, Composite> itemViewer;
	private final AutoUpdate autoUpdate;
	private final Header<I> header;

	static BackgroundProcessor createBackgroundProcessor() {
		return new BackgroundProcessor(new SwtUiThreadDispatcher());
	}

	/**
	 *
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	SwtTimelineCompound(ItemProvider<I> itemProvider, ItemUiFactory<I, Composite> itemUiFactory, SessionStorage<I> sessionStorage) {
		Assertion.check(itemProvider != null, "ITEM_PROVIDER_MUST_NOT_BE_NULL");
		Assertion.check(itemUiFactory != null, "ITEM_UI_FACTORY_MUST_NOT_BE_NULL");
		Assertion.check(sessionStorage != null, "SESSION_STORAGE_MUST_NOT_BE_NULL");

		Timeline<I> timeline = new Timeline<>(itemProvider, sessionStorage);
		itemViewer = new ItemViewer<>(new SwtItemViewerCompound<>(timeline, itemUiFactory));
		header = new Header<>(timeline);
		autoUpdate = new SwtAutoUpdate<>(header, itemViewer, 5_000);
	}

	ItemViewer<I, Composite> getItemViewer() {
		return this.itemViewer;
	}

	Header<I> getHeader() {
		return this.header;
	}

	AutoUpdate getAutoUpdate() {
		return autoUpdate;
	}

}