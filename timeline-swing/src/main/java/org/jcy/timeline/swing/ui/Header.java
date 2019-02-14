package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.Timeline;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class Header<I extends Item> {

	static final String TITLE = "Timeline";
	private final BackgroundProcessor backgroundProcessor;
	private Timeline<I> timeline;
	JPanel component;
	JButton fetchNew;
	JLabel title;

	public JPanel getComponent() {
		return this.component;
	}

	public JLabel getTitle() {
		return this.title;
	}

	public void setTitle(JLabel title) {
		this.title = title;
	}

	/**
	 *
	 * @param timeline
	 */
	Header(Timeline<I> timeline) {
		// TODO - implement Header.Header
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param timeline
	 * @param backgroundProcessor
	 */
	Header(Timeline<I> timeline, BackgroundProcessor backgroundProcessor) {
		// TODO - implement Header.Header
		throw new UnsupportedOperationException();
	}

	void createUi() {
		// TODO - implement Header.createUi
		throw new UnsupportedOperationException();
	}

	void update() {
		// TODO - implement Header.update
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param listener
	 */
	void onFetchNew(ActionListener listener) {
		// TODO - implement Header.onFetchNew
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param count
	 */
	private void update(int count) {
		// TODO - implement Header.update
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param listener
	 * @param evt
	 */
	private void notifyAboutFetchRequest(ActionListener listener, ActionEvent evt) {
		// TODO - implement Header.notifyAboutFetchRequest
		throw new UnsupportedOperationException();
	}

	private void createComponent() {
		// TODO - implement Header.createComponent
		throw new UnsupportedOperationException();
	}

	private void createTitle() {
		// TODO - implement Header.createTitle
		throw new UnsupportedOperationException();
	}

	private void createFetchNew() {
		// TODO - implement Header.createFetchNew
		throw new UnsupportedOperationException();
	}

	private void layout() {
		// TODO - implement Header.layout
		throw new UnsupportedOperationException();
	}

}