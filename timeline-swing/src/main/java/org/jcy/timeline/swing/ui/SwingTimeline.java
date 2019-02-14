package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import java.awt.Container;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;
import java.awt.Component;
import javax.swing.JPanel;

public class SwingTimeline<I extends Item> {

	private final ItemViewer<I, Container> itemViewer;
	private final AutoUpdate<I, Container> autoUpdate;
	private final Header<I> header;

	/**
	 *
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	public void SwingTimeline(ItemProvider<I> itemProvider, ItemUiFactory<I, Container> itemUiFactory, SessionStorage<I> sessionStorage) {
		// TODO - implement SwingTImeline.SwingTimeline
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param compound
	 */
	void SwingTimeline(SwingTimelineCompound<I> compound) {
		// TODO - implement SwingTImeline.SwingTimeline
		throw new UnsupportedOperationException();
	}

	public Component getComponent() {
		// TODO - implement SwingTImeline.getComponent
		throw new UnsupportedOperationException();
	}

	public void startAutoRefresh() {
		// TODO - implement SwingTImeline.startAutoRefresh
		throw new UnsupportedOperationException();
	}

	public void stopAutoRefresh() {
		// TODO - implement SwingTImeline.stopAutoRefresh
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		// TODO - implement SwingTImeline.setTitle
		throw new UnsupportedOperationException();
	}

	private JPanel initialize() {
		// TODO - implement SwingTImeline.initialize
		throw new UnsupportedOperationException();
	}

	private JPanel createComponent() {
		// TODO - implement SwingTImeline.createComponent
		throw new UnsupportedOperationException();
	}

}