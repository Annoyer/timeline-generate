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
	 * Move to the left of {@param control}.
	 *
	 * @param control the control the side is attached to
	 */
	public FormDatas atLeftTo(Control control) {
		return atLeftTo(control, 0);
	}

	/**
	 * Move to the left of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 */
	public FormDatas atLeftTo(Control control, int margin) {
		return atLeftTo(control, margin, SWT.DEFAULT);
	}

	/**
	 * Move to the left of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 * @param alignment the alignment of the side to the control it is attached to
	 */
	public FormDatas atLeftTo(Control control, int margin, int alignment) {
		formData.left = new FormAttachment(control, margin, alignment);
		return this;
	}

	/**
	 * Move from left.
	 *
	 * @param numerator the percentage of the position
	 */
	public FormDatas fromLeft(int numerator) {
		return fromLeft(numerator, 0);
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
	 * Move to the left of {@param control}.
	 *
	 * @param control the control the side is attached to
	 */
	public FormDatas atRightTo(Control control) {
		return atRightTo(control, 0);
	}

	/**
	 * Move to the right of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 */
	public FormDatas atRightTo(Control control, int margin) {
		return atRightTo(control, margin, SWT.DEFAULT);
	}

	/**
	 * Move to the right of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 * @param alignment the alignment of the side to the control it is attached to
	 */
	public FormDatas atRightTo(Control control, int margin, int alignment) {
		formData.right = new FormAttachment(control, -margin, alignment);
		return this;
	}

	/**
	 * Move from right.
	 *
	 * @param numerator the percentage of the position
	 */
	public FormDatas fromRight(int numerator) {
		return fromRight(numerator, 0);
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
	 * Move from top.
	 *
	 * @param numerator the percentage of the position
	 */
	public FormDatas fromTop(int numerator) {
		return fromTop(numerator, 0);
	}

	/**
	 * Move from top.
	 *
	 * @param numerator the percentage of the position
	 * @param margin the offset of the side from the position
	 */
	public FormDatas fromTop(int numerator, int margin) {
		formData.top = new FormAttachment(numerator, margin);
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

	/**
	 * Move to the bottom of {@param control}.
	 *
	 * @param control the control the side is attached to
	 */
	public FormDatas atBottomTo(Control control) {
		return atBottomTo(control, 0);
	}

	/**
	 * Move to the bottom of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 */
	public FormDatas atBottomTo(Control control, int margin) {
		return atBottomTo(control, margin, SWT.DEFAULT);
	}

	/**
	 * Move to the bottom of {@param control}.
	 *
	 * @param control the control the side is attached to
	 * @param margin the offset of the side from the control
	 * @param alignment the alignment of the side to the control it is attached to

	 */
	public FormDatas atBottomTo(Control control, int margin, int alignment) {
		formData.bottom = new FormAttachment(control, -margin, alignment);
		return this;
	}

	/**
	 * Move from bottom.
	 *
	 * @param numerator the percentage of the position
	 */
	public FormDatas fromBottom(int numerator) {
		return fromBottom(numerator, 0);
	}

	/**
	 * Move from bottom.
	 *
	 * @param numerator the percentage of the position
	 * @param margin the offset of the side from the position
	 */
	public FormDatas fromBottom(int numerator, int margin) {
		formData.bottom = new FormAttachment(DENOMINATOR - numerator, -margin);
		return this;
	}

	/**
	 * Set the width.
	 *
	 * @param width width
	 */
	public FormDatas withWidth(int width) {
		formData.width = width;
		return this;
	}

	/**
	 * Set the height.
	 *
	 * @param height height
	 */
	public FormDatas withHeight(int height) {
		formData.height = height;
		return this;
	}

	public FormData getFormData() {
		return this.formData;
	}

	private FormDatas(Control control) {
		formData = new FormData();
		control.setLayoutData(formData);
	}

}