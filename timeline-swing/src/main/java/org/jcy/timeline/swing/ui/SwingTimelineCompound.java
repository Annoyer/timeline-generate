package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemViewer;
import java.awt.Container;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;

public class SwingTimelineCompound<I extends Item> {

	private final ItemViewer<I, Container> itemViewer;
	private final AutoUpdate autoUpdate;
	private final Header<I> header;

	static BackgroundProcessor createBackgroundProcessor() {
		return new BackgroundProcessor(new SwingUiThreadDispatcher());
	}

	/**
	 * Build the itemView, autoUpdate task and application header.
	 *
	 * @param itemProvider itemProvider
	 * @param itemUiFactory itemUiFactory
	 * @param sessionStorage sessionStorage
	 */
	public SwingTimelineCompound(ItemProvider<I> itemProvider, ItemUiFactory<I, Container> itemUiFactory, SessionStorage<I> sessionStorage) {
		Assertion.check(itemProvider != null, "ITEM_PROVIDER_MUST_NOT_BE_NULL");
		Assertion.check(itemUiFactory != null, "ITEM_UI_FACTORY_MUST_NOT_BE_NULL");
		Assertion.check(sessionStorage != null, "SESSION_STORAGE_MUST_NOT_BE_NULL");

		Timeline<I> timeline = new Timeline<>(itemProvider, sessionStorage);
		itemViewer = new ItemViewer<>(this.createItemViewerCompound(timeline, itemUiFactory));
		header = new Header<>(timeline);
		autoUpdate = new SwingAutoUpdate<>(this.getHeader(), this.getItemViewer(), 5_000);
	}

	SwingItemViewerCompound<I> createItemViewerCompound(Timeline<I> timeline, ItemUiFactory<I, Container> itemUiFactory) {
		return new SwingItemViewerCompound<>(timeline, itemUiFactory);
	}

	ItemViewer<I, Container> getItemViewer() {
		return this.itemViewer;
	}

	Header<I> getHeader() {
		return this.header;
	}

	AutoUpdate getAutoUpdate() {
		return this.autoUpdate;
	}

}