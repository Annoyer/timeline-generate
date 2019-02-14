package org.jcy.timeline.core.provider.git;

import org.eclipse.jgit.lib.ObjectId;
import org.jcy.timeline.core.model.Item;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jcy.timeline.util.Assertion;

/**
 * Git Commit Item.
 */
public class GitItem extends Item {

	private final String content;
	private final String author;

	public String getContent() {
		return this.content;
	}

	public String getAuthor() {
		return this.author;
	}

	/**
	 * Create a new commit item.
	 *
	 * @param id commit id.
	 * @param timeStamp commit time.
	 * @param author author.
	 * @param content commit message.
	 */
	public GitItem(String id, long timeStamp, String author, String content) {
		super(timeStamp, id);
		Assertion.check(author != null, "AUTHOR_MUST_NOT_BE_NULL");
		Assertion.check(content != null, "CONTENT_MUST_NOT_BE_NULL");

		this.author = author;
		this.content = content;
	}

	/**
	 * Get the commit message from the commit.
	 *
	 * @param commit commit
	 */
	static String getContent(RevCommit commit) {
		return commit.getShortMessage();
	}

	/**
	 * Get the author name of the commit.
	 *
	 * @param commit commit
	 */
	static String getAuthor(RevCommit commit) {
		return commit.getAuthorIdent().getName();
	}

	/**
	 * Get the commit time.
	 *
	 * @param commit commit
	 */
	static long getTimeStamp(RevCommit commit) {
		return commit.getCommitterIdent().getWhen().getTime();
	}

	/**
	 * Get the commit Id.
	 * @param commit
	 */
	static String getId(RevCommit commit) {
		return ObjectId.toString(commit.getId());
	}

	/**
	 * Create a new {@link GitItem} by {@param commit}.
	 *
	 * @param commit git commit info.
	 */
	public static GitItem ofCommit(RevCommit commit) {
		return new GitItem(getId(commit), getTimeStamp(commit), getAuthor(commit), getContent(commit));
	}

}