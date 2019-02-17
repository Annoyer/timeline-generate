package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;

import java.awt.*;

import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;

import javax.swing.JPanel;

public class SwingTimeline<I extends Item> {

	private final ItemViewer<I, Container> itemViewer;
	private final AutoUpdate autoUpdate;
	private final Header<I> header;
	private final JPanel component;

	public SwingTimeline(ItemProvider<I> itemProvider, ItemUiFactory<I, Container> itemUiFactory, SessionStorage<I> sessionStorage) {
		this(new SwingTimelineCompound<>(itemProvider, itemUiFactory, sessionStorage));
	}

	SwingTimeline(SwingTimelineCompound<I> compound) {
		itemViewer = compound.getItemViewer();
		header = compound.getHeader();
		autoUpdate = compound.getAutoUpdate();
		component = initialize();
	}

	public Component getComponent() {
		return component;
	}

	public void startAutoRefresh() {
		autoUpdate.start();
	}

	public void stopAutoRefresh() {
		autoUpdate.stop();
	}

	public void setTitle(String title) {
		header.setTitle(title);
	}

	private JPanel initialize() {
		header.createUi();
		itemViewer.createUi(null);
		JPanel result = createComponent();
		itemViewer.initialize();
		header.onFetchNew(event -> itemViewer.fetchNew());
		return result;
	}

	private JPanel createComponent() {
		JPanel result = new JPanel();
		result.setLayout(new BorderLayout());
		result.add(header.getComponent(), BorderLayout.NORTH);
		result.add(itemViewer.getUiRoot(), BorderLayout.CENTER);
		return result;
	}

}