package org.jcy.timeline.swt.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.model.SessionStorage;
import org.eclipse.swt.widgets.Control;
import org.jcy.timeline.swt.ui.util.FormDatas;

public class SwtTimeline<I extends Item> {

	private final ItemViewer<I, Composite> itemViewer;
	private final AutoUpdate<I, Composite> autoUpdate;
	private final Header<I> header;
	private final Composite control;

	/**
	 *
	 * @param parent
	 * @param itemProvider
	 * @param itemUiFactory
	 * @param sessionStorage
	 */
	public SwtTimeline(Composite parent, ItemProvider<I> itemProvider, ItemUiFactory<I, Composite> itemUiFactory, SessionStorage<I> sessionStorage) {
		this(parent, new SwtTimelineCompound<>(itemProvider, itemUiFactory, sessionStorage));
	}

	/**
	 *
	 * @param parent
	 * @param compound
	 */
	SwtTimeline(Composite parent, SwtTimelineCompound<I> compound) {
		itemViewer = compound.getItemViewer();
		header = compound.getHeader();
		autoUpdate = compound.getAutoUpdate();
		control = initialize(parent);
	}

	public Control getControl() {
		return control;
	}

	public void startAutoRefresh() {
		autoUpdate.start();
	}

	public void stopAutoRefresh() {
		autoUpdate.stop();
	}

	/**
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		header.setTitle(title);
	}

	/**
	 *
	 * @param parent
	 */
	private Composite initialize(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		header.createUi(result);
		itemViewer.createUi(result);
		layout(result);
		itemViewer.initialize();
		header.onFetchNew(event -> itemViewer.fetchNew());
		return result;
	}

	/**
	 *
	 * @param control
	 */
	private void layout(Composite control) {
		control.setLayout(new FormLayout());
		FormDatas.attach(header.getControl()).toLeft().toTop().toRight();
		FormDatas.attach(itemViewer.getUiRoot()).toLeft().atTopTo(header.getControl()).toRight().toBottom();
	}

}