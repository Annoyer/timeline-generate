package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.FetchOperation;
import org.jcy.timeline.core.ui.ItemUiList;

import javax.swing.*;

import org.jcy.timeline.core.ui.ItemUiMap;

import java.awt.*;

import org.jcy.timeline.util.BackgroundProcessor;

public class SwingItemUiList<I extends Item> extends ItemUiList<I, Container> {

	JScrollPane uiRoot;
	JButton fetchMore;
	JPanel content;

	SwingItemUiList(ItemUiMap<I, Container> itemUiMap) {
		this(itemUiMap, SwingTimelineCompound.createBackgroundProcessor());
	}

	SwingItemUiList(ItemUiMap<I, Container> itemUiMap, BackgroundProcessor backgroundProcessor) {
		super(itemUiMap, backgroundProcessor);
	}

	/**
	 * Only one thread could do the fetch more operation at the same time.
	 */
	protected void beforeContentUpdate() {
		content.remove(fetchMore);
	}

	protected void afterContentUpdate() {
		content.add(fetchMore, SwingItemUi.createUiItemConstraints());
		content.getParent().doLayout();
	}

	/**
	 * Create UI.
	 *
	 * @param parent parent container.
	 */
	protected void createUi(Container parent) {
		createContent();
		createComponent();
		createFetchMore();
	}

	protected Container getContent() {
		return content;
	}

	protected JScrollPane getUiRoot() {
		return uiRoot;
	}

	private void createContent() {
		content = new JPanel();
		content.setLayout(new GridBagLayout());
		content.setBackground(Resources.WHITE);
	}

	private void createComponent() {
		uiRoot = new JScrollPane(content);
		uiRoot.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

	private void createFetchMore() {
		fetchMore = new JButton("more");
		fetchMore.addActionListener(event -> fetchInBackground(FetchOperation.MORE));
		content.add(fetchMore);
	}
}