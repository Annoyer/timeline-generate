package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemUiList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jcy.timeline.core.ui.ItemUiMap;
import java.awt.Container;
import org.jcy.timeline.util.BackgroundProcessor;
import U;

class SwingItemUiList<I extends Item> extends ItemUiList<I, Container> {

	JScrollPane uiRoot;
	JButton fetchMore;
	JPanel content;

	/**
	 *
	 * @param parameter
	 */
	SwingItemUiList(ItemUiMap<I, Container> parameter) {
		// TODO - implement SwingItemUiList.SwingItemUiList
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parameter
	 * @param backgroundProcessor
	 */
	SwingItemUiList(ItemUiMap<I, Container> parameter, BackgroundProcessor backgroundProcessor) {
		// TODO - implement SwingItemUiList.SwingItemUiList
		throw new UnsupportedOperationException();
	}

	private void createContent() {
		// TODO - implement SwingItemUiList.createContent
		throw new UnsupportedOperationException();
	}

	private void createComponent() {
		// TODO - implement SwingItemUiList.createComponent
		throw new UnsupportedOperationException();
	}

	private void createFetchMore() {
		// TODO - implement SwingItemUiList.createFetchMore
		throw new UnsupportedOperationException();
	}

	protected void beforeContentUpdate() {
		// TODO - implement SwingItemUiList.beforeContentUpdate
		throw new UnsupportedOperationException();
	}

	protected void afterContentUpdate() {
		// TODO - implement SwingItemUiList.afterContentUpdate
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	protected void createUi(Container parent) {
		// TODO - implement SwingItemUiList.createUi
		throw new UnsupportedOperationException();
	}

	protected Container getContent() {
		// TODO - implement SwingItemUiList.getContent
		throw new UnsupportedOperationException();
	}

	protected Container getUiRoot() {
		// TODO - implement SwingItemUiList.getUiRoot
		throw new UnsupportedOperationException();
	}

}