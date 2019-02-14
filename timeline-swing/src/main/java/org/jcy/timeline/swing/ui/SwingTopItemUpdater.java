package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemUpdater;
import java.awt.Container;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import java.awt.Component;
import org.jcy.timeline.core.ui.ItemUi;

import javax.swing.*;

public class SwingTopItemUpdater<I extends Item> extends TopItemUpdater<I, Container> {

	private final UiThreadDispatcher uiThreadDispatcher;
	private final SwingItemUiList<I> itemUiList;

	SwingTopItemUpdater(Timeline timeline, ItemUiMap<I, Container> itemUiMap, SwingItemUiList<I> itemUiList) {
		this(timeline, itemUiMap, itemUiList, new SwingUiThreadDispatcher());
	}

	SwingTopItemUpdater(Timeline timeline, ItemUiMap<I, Container> itemUiMap, SwingItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		super(timeline, itemUiMap);
		this.uiThreadDispatcher = dispatcher;
		this.itemUiList = itemUiList;
	}

	protected boolean isBelowTop(ItemUi<I> itemUi) {
		Component component = ((SwingItemUi<I>) itemUi).getComponent();
		if (component.isShowing()) {
			return isBelowTop(component);
		}
		return false;
	}

	private boolean isBelowTop(Component component) {
		double y1 = itemUiList.getUiRoot().getLocationOnScreen().getY();
		double y2 = component.getLocationOnScreen().getY();
		return y2 - y1 >= 0;
	}

	protected void register() {
		JScrollPane jScrollPane = itemUiList.getUiRoot();
		BoundedRangeModel model = jScrollPane.getVerticalScrollBar().getModel();
		uiThreadDispatcher.dispatch(() -> model.addChangeListener(evt -> update()));
	}


}