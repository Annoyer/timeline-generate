package org.jcy.timeline.swt.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ScrollBar;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.TopItemUpdater;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.util.UiThreadDispatcher;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.eclipse.swt.widgets.Control;
import org.jcy.timeline.core.ui.ItemUi;

public class SwtTopItemUpdater<I extends Item> extends TopItemUpdater<I, Composite> {

	static final int TOP_OFF_SET = 5;
	private final UiThreadDispatcher uiThreadDispatcher;
	private final SwtItemUiList<I> itemUiList;

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 */
	SwtTopItemUpdater(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList) {
		this(timeline, itemUiMap, itemUiList, new SwtUiThreadDispatcher());
	}

	/**
	 *
	 * @param timeline
	 * @param itemUiMap
	 * @param itemUiList
	 * @param dispatcher
	 */
	SwtTopItemUpdater(Timeline<I> timeline, ItemUiMap<I, Composite> itemUiMap, SwtItemUiList<I> itemUiList, UiThreadDispatcher dispatcher) {
		super(timeline, itemUiMap);
		this.uiThreadDispatcher = dispatcher;
		this.itemUiList = itemUiList;
	}

	/**
	 *
	 * @param control
	 */
	private boolean isBelowTop(Control control) {
		Composite root = itemUiList.getUiRoot();
		int y = control.getDisplay().map(control.getParent(), root, control.getLocation()).y;
		return y + TOP_OFF_SET >= 0;
	}

	protected void register() {
		ScrollBar verticalBar = itemUiList.getUiRoot().getVerticalBar();
		uiThreadDispatcher.dispatch(() -> verticalBar.addListener(SWT.Selection, evt -> update()));
	}

	/**
	 *
	 * @param itemUi
	 */
	protected boolean isBelowTop(ItemUi<I> itemUi) {
		Control control = ((SwtItemUi<I>) itemUi).getControl();
		if (control.isVisible()) {
			return isBelowTop(control);
		}
		return false;
	}

}