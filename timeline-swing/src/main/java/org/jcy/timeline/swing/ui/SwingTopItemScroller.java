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
	private final SwingItemUiList<I> itemUiList;
	private final Timeline<I> timeline;

	SwingTopItemScroller(Timeline<I> timeline, ItemUiMap<I, Container> itemUiMap, SwingItemUiList<I> itemUiList) {
		this(timeline, itemUiMap, itemUiList, new SwingUiThreadDispatcher());
	}

	SwingTopItemScroller(Timeline<I> timeline,
						 ItemUiMap<I, Container> itemUiMap,
						 SwingItemUiList<I> itemUiList,
						 UiThreadDispatcher dispatcher) {
		this.uiThreadDispatcher = dispatcher;
		this.itemUiList = itemUiList;
		this.itemUiMap = itemUiMap;
		this.timeline = timeline;
	}

	public void scrollIntoView() {
		uiThreadDispatcher.dispatch(() -> doScrollIntoView());
	}
	
	private void doScrollIntoView() {
		if (timeline.getTopItem().isPresent()) {
			SwingItemUi<I> itemUi = getItemUi();
			if (itemUi != null) {
				Component component = itemUi.getComponent();
				if (component.isShowing()) {
					updateVerticalScrollbarSelection(component);
				}
			}
		}
	}

	void setScrollbarSelection(int newValue) {
		getContentPane().getVerticalScrollBar().getModel().setValue(newValue);
	}

	private SwingItemUi<I> getItemUi() {
		I item = timeline.getTopItem().get();
		return (SwingItemUi<I>) itemUiMap.findByItemId(item.getId());
	}

	private void updateVerticalScrollbarSelection(Component component) {
		double verticalLocation = computeVerticalLocation(component);
		if (verticalLocation < TOP_POSITION) {
			updateVerticalScrollbarSelection(verticalLocation);
		}
	}

	private void updateVerticalScrollbarSelection(double verticalLocation) {
		int oldValue = getContentPane().getVerticalScrollBar().getModel().getValue();
		setScrollbarSelection(oldValue - 15 + (int) verticalLocation);
	}

	private double computeVerticalLocation(Component component) {
		double y1 = itemUiList.getUiRoot().getLocationOnScreen().getY();
		double y2 = component.getLocationOnScreen().getY();
		return y2 - y1;
	}

	private JScrollPane getContentPane() {
		return itemUiList.getUiRoot();
	}

}