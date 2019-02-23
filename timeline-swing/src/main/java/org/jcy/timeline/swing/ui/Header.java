package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.core.model.Timeline;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.jcy.timeline.swing.ui.SwingTimelineCompound;

/**
 * Application Header.
 *
 * @param <I>
 */
public class Header<I extends Item> {

	static final String TITLE = "Timeline";
	private final BackgroundProcessor backgroundProcessor;
	private Timeline<I> timeline;
	JPanel component;
	JButton fetchNew;
	JLabel title;

	/**
	 * Create the header with a new background processor.
	 *
	 * @param timeline timeline.
	 */
	Header(Timeline<I> timeline) {
		this(timeline, SwingTimelineCompound.createBackgroundProcessor());
	}

	Header(Timeline<I> timeline, BackgroundProcessor backgroundProcessor) {
		this.backgroundProcessor = backgroundProcessor;
		this.timeline = timeline;
	}

	/**
	 * Create all UI components in the header.
	 */
	void createUi() {
		createComponent();
		createTitle();
		createFetchNew();
		layout();
	}

	/**
	 * Update the 'new commit count' in header.
	 */
	public void update() {
		backgroundProcessor.process(() -> {
			int count = timeline.getNewCount();
			backgroundProcessor.dispatchToUiThread(() -> update(count));
		});
	}

	/**
	 * Bind the action on the button {@link Header#fetchNew}.
	 *
	 * @param listener the action
	 */
	void onFetchNew(ActionListener listener) {
		fetchNew.addActionListener(evt -> notifyAboutFetchRequest(listener, evt));
	}

	Component getComponent() {
		return this.component;
	}

	public String getTitle() {
		return title.getText();
	}

	void setTitle(String title) {
		Assertion.check(title != null, "TITLE_MUST_NOT_BE_NULL");

		this.title.setText(title);
	}

	/**
	 * If there is new commits, show the fetchNew button with the count of new commits.
	 *
	 * @param count the count of new commits.
	 */
	private void update(int count) {
		fetchNew.setText(count + " new");
		fetchNew.setVisible(count > 0);
	}

	/**
	 * Notify about the fetch new request when the 'fetchNew' button is clicked.
	 *
	 * @param listener
	 * @param evt
	 */
	private void notifyAboutFetchRequest(ActionListener listener, ActionEvent evt) {
		listener.actionPerformed(new ActionEvent(component, evt.getID(), evt.getActionCommand()));
		fetchNew.setVisible(false);
	}

	private void createComponent() {
		component = new JPanel();
	}

	private void createTitle() {
		title = new JLabel(TITLE);
		Resources.changeFontSize(title, 10);
	}

	private void createFetchNew() {
		fetchNew = new JButton();
		fetchNew.setVisible(false);
	}

	private void layout() {
		component.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
		component.add(title);
		component.add(fetchNew);
	}

}