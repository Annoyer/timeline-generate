package org.jcy.timeline.swt.git;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.jcy.timeline.swt.ui.Resources;
import org.jcy.timeline.swt.ui.SwtItemUi;
import org.jcy.timeline.core.provider.git.GitItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Control;
import org.jcy.timeline.swt.ui.util.FormDatas;
import org.jcy.timeline.util.NiceTime;

class GitItemUi implements SwtItemUi<GitItem> {

	private final Composite control;
	private final Label content;
	private final GitItem item;
	private final Label author;
	private final Label time;


	/**
	 *
	 * @param parent
	 * @param item
	 * @param index
	 */
	GitItemUi(Composite parent, GitItem item, int index) {
		this.item = item;
		this.control = createControl(parent);
		this.author = createAutor();
		this.time = createTime();
		this.content = createContent();
		layout();
		adjustDrawingOrderPlacement(parent, index);
	}

	public Control getControl() {
		return control;
	}

	@Override()
	public void update() {
		time.setText(getPrettyTime());
	}

	String getTime() {
		return time.getText();
	}


	private static Composite createControl(Composite composite) {
		Composite result = new Composite(composite, SWT.NONE);
		result.setBackground(Resources.getColorWhite());
		result.setBackgroundMode(SWT.INHERIT_DEFAULT);
		return result;
	}

	private Label createAutor() {
		Label result = new Label(control, SWT.NONE);
		result.setText(item.getAuthor());
		Resources.changeFontHeight(result, 2);
		return result;
	}

	private Label createTime() {
		Label time = new Label(control, SWT.NONE);
		time.setText(getPrettyTime());
		Resources.changeFontHeight(time, 2);
		return time;
	}

	private Label createContent() {
		Label content = new Label(control, SWT.WRAP);
		content.setText(item.getContent());
		Resources.changeFontHeight(content, 1);
		return content;
	}

	/**
	 * Set the layout og Git UI item.
	 */
	private void layout() {
		control.setLayout(new FormLayout());
		FormDatas.attach(author).toLeft(Resources.MARGIN).toTop(Resources.MARGIN);
		FormDatas.attach(time).toTop(Resources.MARGIN).toRight(Resources.MARGIN);
		FormDatas.attach(content)
				.toLeft(Resources.MARGIN)
				.atTopTo(author, Resources.MARGIN)
				.toRight(Resources.MARGIN)
				.toBottom(Resources.MARGIN);
	}

	/**
	 * Move the item to the {@param index} position in the item list.
	 *
	 * @param parent context
	 * @param index index
	 */
	private void adjustDrawingOrderPlacement(Composite parent, int index) {
		if (parent.getChildren().length > index) {
			Control child = parent.getChildren()[index];
			control.moveAbove(child);
		}
	}

	private String getPrettyTime() {
		return NiceTime.format(item.getTimeStamp());
	}

}