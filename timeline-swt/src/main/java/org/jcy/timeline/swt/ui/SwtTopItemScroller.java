package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemScroller;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.model.Timeline;
import org.eclipse.swt.widgets.Control;

public class SwtTopItemScroller<I extends Item> implements TopItemScroller<I> {

	static final int TOP_POSITION = 5;
	private final UiThreadDispatcher uiThreadDispatcher;
	private final ItemUiMap<I, Composite> itemUiMap;
	private final SwtItemUiList<I> itemUiList;
	private final Timeline<I> timeline;

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 */
	SwtTopItemScroller(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList) {
		// TODO - implement SwtTopItemScroller.SwtTopItemScroller
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 * @param dispatcher
	 */
	SwtTopItemScroller(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		// TODO - implement SwtTopItemScroller.SwtTopItemScroller
		throw new UnsupportedOperationException();
	}

	public void doScrollIntoView() {
		// TODO - implement SwtTopItemScroller.doScrollIntoView
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param newValue
	 */
	void setScrollbarSelection(int newValue) {
		// TODO - implement SwtTopItemScroller.setScrollbarSelection
		throw new UnsupportedOperationException();
	}

	private SwtItemUi<I> getItemUi() {
		// TODO - implement SwtTopItemScroller.getItemUi
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param control
	 */
	private void updateVerticalScrollbarSelection(Control control) {
		// TODO - implement SwtTopItemScroller.updateVerticalScrollbarSelection
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param verticalPosition
	 */
	private void updateVerticalScrollbarSelection(int verticalPosition) {
		// TODO - implement SwtTopItemScroller.updateVerticalScrollbarSelection
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param control
	 */
	private int computeVerticalPosition(Control control) {
		// TODO - implement SwtTopItemScroller.computeVerticalPosition
		throw new UnsupportedOperationException();
	}

	private Composite getContent() {
		// TODO - implement SwtTopItemScroller.getContent
		throw new UnsupportedOperationException();
	}

	public void scrollIntoView() {
		// TODO - implement SwtTopItemScroller.scrollIntoView
		throw new UnsupportedOperationException();
	}

}