package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemUiList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.jcy.timeline.util.BackgroundProcessor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

class SwtItemUiList<I extends Item> extends ItemUiList<I, Composite> {

	ScrolledComposite uiRoot;
	Composite content;
	Button fetchMore;

	/**
	 *
	 * @param itemUiMap
	 */
	SwtItemUiList(ItemUiMap<I, Composite> itemUiMap) {
		// TODO - implement SwtItemUiList.SwtItemUiList
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemUiMap
	 * @param backgroundProcessor
	 */
	SwtItemUiList(ItemUiMap<I, Composite> itemUiMap, BackgroundProcessor backgroundProcessor) {
		// TODO - implement SwtItemUiList.SwtItemUiList
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	private void createControl(Composite parent) {
		// TODO - implement SwtItemUiList.createControl
		throw new UnsupportedOperationException();
	}

	private void createContent() {
		// TODO - implement SwtItemUiList.createContent
		throw new UnsupportedOperationException();
	}

	private void createFetchMore() {
		// TODO - implement SwtItemUiList.createFetchMore
		throw new UnsupportedOperationException();
	}

	private void layoutContent() {
		// TODO - implement SwtItemUiList.layoutContent
		throw new UnsupportedOperationException();
	}

	private Point computePreferredContentSize() {
		// TODO - implement SwtItemUiList.computePreferredContentSize
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param itemControl
	 * @param width
	 */
	private void setLayoutData(Control itemControl, int width) {
		// TODO - implement SwtItemUiList.setLayoutData
		throw new UnsupportedOperationException();
	}

	protected void beforeContentUpdate() {
		// TODO - implement SwtItemUiList.beforeContentUpdate
		throw new UnsupportedOperationException();
	}

	protected void afterContentUpdate() {
		// TODO - implement SwtItemUiList.afterContentUpdate
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	protected void createUi(Composite parent) {
		// TODO - implement SwtItemUiList.createUi
		throw new UnsupportedOperationException();
	}

	protected Composite getContent() {
		// TODO - implement SwtItemUiList.getContent
		throw new UnsupportedOperationException();
	}

	protected Composite getUiRoot() {
		// TODO - implement SwtItemUiList.getUiRoot
		throw new UnsupportedOperationException();
	}

}