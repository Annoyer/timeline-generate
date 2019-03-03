package org.jcy.timeline.swt.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

public class FormDatas {

	static final int DENOMINATOR = 100;
	private final FormData formData;

	/**
	 * Attach the {@param control} to a new FormDatas instance.
	 *
	 * @param control control
	 */
	public static FormDatas attach(Control control) {
		return new FormDatas(control);
	}

	/**
	 * Move to the left.
	 */
	public FormDatas toLeft() {
		return toLeft(0);
	}

	/**
	 * Move to the left with {@param margin}.
	 *
	 * @param margin margin
	 */
	public FormDatas toLeft(int margin) {
		formData.left = new FormAttachment(0, margin);
		return this;
	}

	/**
	 * Move from left.
	 *
	 * @param numerator the percentage of the position
	 * @param margin the offset of the side from the position
	 */
	public FormDatas fromLeft(int numerator, int margin) {
		formData.left = new FormAttachment(numerator, margin);
		return this;
	}

	/**
	 * Move to the right.
	 */
	public FormDatas toRight() {
		return toRight(0);
	}

	/**
	 * Move to the right with {@param margin}.
	 *
	 * @param margin margin
	 */
	public FormDatas toRight(int margin) {
		formData.right = new FormAttachment(DENOMINATOR, -margin);
		return this;
	}

	/**
	 * Move from right.
	 *
	 * @param numerator the percentage of the position
	 * @param margin the offset of the side from the position
	 */
	public FormDatas fromRight(int numerator, int margin) {
		formData.right = new FormAttachment(DENOMINATOR - numerator, -margin);
		return this;
	}

	/**
	 * Move to the top.
	 */
	public FormDatas toTop() {
		return toTop(0);
	}

	/**
	 * Move to the top with {@param margin}.
	 *
	 * @param margin margin
	 */
	public FormDatas toTop(int margin) {
		formData.top = new FormAttachment(0, margin);
		return this;
	}

	/**
	 * Move to the top of {@param control}.
	 *
	 * @param control the control the side is attached to
	 */
	public FormDatas atTopTo(Control control) {
		return atTopTo(control, 0);
	}

	/**
	 * Move to the top of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 */
	public FormDatas atTopTo(Control control, int margin) {
		return atTopTo(control, margin, SWT.DEFAULT);
	}

	/**
	 * Move to the top of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 * @param alignment the alignment of the side to the control it is attached to
	 */
	public FormDatas atTopTo(Control control, int margin, int alignment) {
		formData.top = new FormAttachment(control, margin, alignment);
		return this;
	}

	/**
	 * Move to bottom.
	 */
	public FormDatas toBottom() {
		return toBottom(0);
	}

	/**
	 * Move to the bottom with {@param margin}.
	 *
	 * @param margin margin
	 */
	public FormDatas toBottom(int margin) {
		formData.bottom = new FormAttachment(DENOMINATOR, -margin);
		return this;
	}

	private FormDatas(Control control) {
		formData = new FormData();
		control.setLayoutData(formData);
	}

}