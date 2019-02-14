package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;
import org.eclipse.swt.widgets.Control;

public class SwtTimeline<I extends Item> {

	private final ItemViewer<I, Composite> itemViewer;
	private final AutoUpdate<I, Composite> autoUpdate;
	private final Header<I> header;
	private final Composite control;

	/**
	 *
	 * @param parent
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	public SwtTimeline(Composite parent, ItemProvider<I> itemProvider, ItemUiFactory<I, Composite> itemUiFactory, SessionStorage<I> sessionStorage) {
		// TODO - implement SwtTimeline.SwtTimeline
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 * @param compound
	 */
	SwtTimeline(Composite parent, SwtTimelineCompound<I> compound) {
		// TODO - implement SwtTimeline.SwtTimeline
		throw new UnsupportedOperationException();
	}

	public Control getControl() {
		// TODO - implement SwtTimeline.getControl
		throw new UnsupportedOperationException();
	}

	public void startAutoRefresh() {
		// TODO - implement SwtTimeline.startAutoRefresh
		throw new UnsupportedOperationException();
	}

	public void stopAutoRefresh() {
		// TODO - implement SwtTimeline.stopAutoRefresh
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		// TODO - implement SwtTimeline.setTitle
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	private Composite initialize(Composite parent) {
		// TODO - implement SwtTimeline.initialize
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param control
	 */
	private void layout(Composite control) {
		// TODO - implement SwtTimeline.layout
		throw new UnsupportedOperationException();
	}

}