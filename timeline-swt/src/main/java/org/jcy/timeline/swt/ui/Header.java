package org.jcy.timeline.swt.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.util.BackgroundProcessor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;

class Header<I extends Item> {

	static final String TITLE = "Timeline";
	private final Timeline<I> timeline;
	private final BackgroundProcessor backgroundProcessor;
	Composite control;
	Button fetchNew;
	Label title;

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

	/**
	 *
	 * @param parent
	 */
	void createUi(Composite parent) {
		// TODO - implement Header.createUi
		throw new UnsupportedOperationException();
	}

	Control getControl() {
		// TODO - implement Header.getControl
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
	void onFetchNew(Listener listener) {
		// TODO - implement Header.onFetchNew
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param title
	 */
	void setTitle(String title) {
		// TODO - implement Header.setTitle
		throw new UnsupportedOperationException();
	}

	String getTitle() {
		// TODO - implement Header.getTitle
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
	private void notifyAboutFetchRequest(Listener listener, Event evt) {
		// TODO - implement Header.notifyAboutFetchRequest
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	private void createControl(Composite parent) {
		// TODO - implement Header.createControl
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