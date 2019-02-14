package org.jcy.timeline.swt.git;

import org.jcy.timeline.swt.ui.SwtItemUi;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.util.NiceTime;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Control;

class GitItemUi implements SwtItemUi<GitItem> {

	private final NiceTime niceTime;
	private final Composite control;
	private final Label content;
	private final GitItem item;
	private final Label author;
	private final Label time;

	String getTime() {
		// TODO - implement GitItemUi.getTime
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param composite
	 */
	private static Composite createControl(Composite composite) {
		// TODO - implement GitItemUi.createControl
		throw new UnsupportedOperationException();
	}

	private Label createAutor() {
		// TODO - implement GitItemUi.createAutor
		throw new UnsupportedOperationException();
	}

	private Label createTime() {
		// TODO - implement GitItemUi.createTime
		throw new UnsupportedOperationException();
	}

	private Label createContent() {
		// TODO - implement GitItemUi.createContent
		throw new UnsupportedOperationException();
	}

	private void layout() {
		// TODO - implement GitItemUi.layout
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 * @param index
	 */
	private void adjustDrawingOrderPlacement(Composite parent, int index) {
		// TODO - implement GitItemUi.adjustDrawingOrderPlacement
		throw new UnsupportedOperationException();
	}

	private String getPrettyTime() {
		// TODO - implement GitItemUi.getPrettyTime
		throw new UnsupportedOperationException();
	}

	public Control getControl() {
		// TODO - implement GitItemUi.getControl
		throw new UnsupportedOperationException();
	}

	@Override
	public void update() {
		// TODO - implement GitItemUi.getControl
		throw new UnsupportedOperationException();
	}
}