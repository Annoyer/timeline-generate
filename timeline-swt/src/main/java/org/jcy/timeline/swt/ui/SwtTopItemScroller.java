package org.jcy.timeline.swt.ui;

import org.eclipse.swt.custom.ScrolledComposite;
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
		this(timeline, itemUiMap, itemUiList, new SwtUiThreadDispatcher());
	}

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 * @param dispatcher
	 */
	SwtTopItemScroller(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		this.uiThreadDispatcher = dispatcher;
		this.itemUiList = itemUiList;
		this.itemUiMap = itemUiMap;
		this.timeline = timeline;
	}

	public void scrollIntoView() {
		uiThreadDispatcher.dispatch(() -> doScrollIntoView());
	}

	public void doScrollIntoView() {
		if (timeline.getTopItem().isPresent()) {
			SwtItemUi<I> itemUi = getItemUi();
			if (itemUi != null) {
				Control control = itemUi.getControl();
				if (control.isVisible()) {
					updateVerticalScrollbarSelection(control);
				}
			}
		}
	}

	/**
	 *
	 * @param newValue
	 */
	void setScrollbarSelection(int newValue) {
		getContent().getVerticalBar().setSelection(newValue);
		((ScrolledComposite) getContent()).setOrigin(0, newValue);
	}

	private SwtItemUi<I> getItemUi() {
		I item = timeline.getTopItem().get();
		return (SwtItemUi<I>) itemUiMap.findByItemId(item.getId());
	}

	/**
	 *
	 * @param control
	 */
	private void updateVerticalScrollbarSelection(Control control) {
		int verticalPosition = computeVerticalPosition(control);
		if (verticalPosition > TOP_POSITION) {
			updateVerticalScrollbarSelection(verticalPosition);
		}
	}

	/**
	 *
	 * @param verticalPosition
	 */
	private void updateVerticalScrollbarSelection(int verticalPosition) {
		int oldValue = getContent().getVerticalBar().getSelection();
		setScrollbarSelection(oldValue - TOP_POSITION + verticalPosition);
	}

	/**
	 *
	 * @param control
	 */
	private int computeVerticalPosition(Control control) {
		Composite root = itemUiList.getUiRoot();
		return control.getDisplay().map(control.getParent(), root, control.getLocation()).y;
	}

	private Composite getContent() {
		return itemUiList.getUiRoot();
	}

}