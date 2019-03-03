package org.jcy.timeline.swing.git;

import org.jcy.timeline.swing.ui.SwingItemUi;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.util.NiceTime;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;
import java.util.Date;

import static org.jcy.timeline.swing.ui.Resources.WHITE;

/**
 * Git Commit Ui Item in Swing.
 */
class GitItemUi implements SwingItemUi<GitItem> {

	/**
	 * Commit Message.
	 */
	private final JTextArea content;
	/**
	 * UI Item Context.
	 */
	private final JPanel component;
	/**
	 * Commit author.
	 */
	private final JLabel author;
	/**
	 * Commit item info.
	 */
	private final GitItem item;
	/**
	 * Commit duration time.
	 */
	private final JLabel time;

	GitItemUi(GitItem item) {
		this.item = item;
		this.component = createComponent();
		this.author = createAuthor(item);
		this.time = createTime();
		this.content = createContent(item);
		layout();
	}

	private JPanel createComponent() {
		JPanel result = new JPanel();
		result.setBackground(WHITE);
		return result;
	}


	private JLabel createAuthor(GitItem item) {
		JLabel result = new JLabel(item.getAuthor());
		result.setOpaque(true);
		result.setBackground(WHITE);
		return result;
	}

	private JLabel createTime() {
		JLabel result = new JLabel(getPrettyTime());
		result.setOpaque(true);
		result.setBackground(WHITE);
		return result;
	}


	private JTextArea createContent(GitItem item) {
		JTextArea result = new JTextArea(item.getContent());
		result.setWrapStyleWord(true);
		result.setLineWrap(true);
		result.setEditable(false);
		return result;
	}

	private void layout() {
		component.setLayout(new BorderLayout(5, 5));
		component.add(author, BorderLayout.WEST);
		component.add(time, BorderLayout.EAST);
		component.add(content, BorderLayout.SOUTH);
	}

	private String getPrettyTime() {
		return NiceTime.format(item.getTimeStamp());
	}

	public Component getComponent() {
		return component;
	}

	/**
	 * Update the duration time util now of this commit.
	 */
	@Override
	public void update() {
		time.setText(getPrettyTime());
	}
}