package org.jcy.timeline.swing.git;

import org.jcy.timeline.swing.ui.SwingItemUi;
import org.jcy.timeline.util.NiceTime;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import org.jcy.timeline.core.provider.git.GitItem;
import java.awt.Component;
import java.awt.GridBagConstraints;

class GitItemUi implements SwingItemUi {

	private final NiceTime niceTime;
	private final JTextArea context;
	private final JPanel component;
	private final JLabel author;
	private final GitItem item;
	private final JLabel time;

	/**
	 *
	 * @param item
	 */
	GitItemUi(GitItem item) {
		// TODO - implement GitItemUi.GitItemUi
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param item
	 * @param niceTime
	 */
	GitItemUi(GitItem item, NiceTime niceTime) {
		// TODO - implement GitItemUi.GitItemUi
		throw new UnsupportedOperationException();
	}

	String getTime() {
		// TODO - implement GitItemUi.getTime
		throw new UnsupportedOperationException();
	}

	private JLabel createComponent() {
		// TODO - implement GitItemUi.createComponent
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param item
	 */
	private JLabel createAuthor(GitItem item) {
		// TODO - implement GitItemUi.createAuthor
		throw new UnsupportedOperationException();
	}

	private JLabel createTime() {
		// TODO - implement GitItemUi.createTime
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param item
	 */
	private JTextArea createContent(GitItem item) {
		// TODO - implement GitItemUi.createContent
		throw new UnsupportedOperationException();
	}

	private void layout() {
		// TODO - implement GitItemUi.layout
		throw new UnsupportedOperationException();
	}

	private String getPrettyTime() {
		// TODO - implement GitItemUi.getPrettyTime
		throw new UnsupportedOperationException();
	}

	Component getComponent() {
		// TODO - implement GitItemUi.getComponent
		throw new UnsupportedOperationException();
	}

	public static GridBagConstraints createUiItemConstraints() {
		// TODO - implement GitItemUi.createUiItemConstraints
		throw new UnsupportedOperationException();
	}

}