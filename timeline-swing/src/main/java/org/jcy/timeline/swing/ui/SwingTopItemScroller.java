package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemScroller;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.ui.ItemUiMap;
import java.awt.Container;
import org.jcy.timeline.core.model.Timeline;
import java.awt.Component;
import javax.swing.JScrollPane;

public class SwingTopItemScroller<I extends Item> implements TopItemScroller {

	static final int TOP_POSITION = 5;
	private final UiThreadDispatcher uiThreadDispatcher;
	private final ItemUiMap<I, Container> itemUiMap;
	private SwingItemUiList<I> itemUiList;
	private final Timeline<I> timeline;

	public void doScrollIntoView() {
		// TODO - implement SwingTopItemScroller.doScrollIntoView
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param newValue
	 */
	void setScrollbarSelection(int newValue) {
		// TODO - implement SwingTopItemScroller.setScrollbarSelection
		throw new UnsupportedOperationException();
	}

	private SwingItemUi<I> getItemUi() {
		// TODO - implement SwingTopItemScroller.getItemUi
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param component
	 */
	private void updateVerticalScrollbarSelection(Component component) {
		// TODO - implement SwingTopItemScroller.updateVerticalScrollbarSelection
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param verticalLocation
	 */
	private void updateVerticalScrollbarSelection(double verticalLocation) {
		// TODO - implement SwingTopItemScroller.updateVerticalScrollbarSelection
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param component
	 */
	private double computeVerticalLocation(Component component) {
		// TODO - implement SwingTopItemScroller.computeVerticalLocation
		throw new UnsupportedOperationException();
	}

	private JScrollPane getContentPane() {
		// TODO - implement SwingTopItemScroller.getContentPane
		throw new UnsupportedOperationException();
	}

	public void scrollIntoView() {
		// TODO - implement SwingTopItemScroller.scrollIntoView
		throw new UnsupportedOperationException();
	}

}