package org.jcy.timeline.core.provider.git;

import org.jcy.timeline.core.model.ItemProvider;
import java.io.File;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import java.util.Optional;
import org.eclipse.jgit.lib.ObjectId;

public class GitItemProvider {

	private final GitOperator operator;

	/**
	 *
	 * @param uri
	 * @param name
	 */
	public GitItemProvider(String uri, String name) {
		// TODO - implement GitItemProvider.GitItemProvider
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uri
	 * @param destination
	 * @param name
	 */
	public GitItemProvider(String uri, File destination, String name) {
		// TODO - implement GitItemProvider.GitItemProvider
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param ancestor
	 * @param fetchCount
	 */
	public List<GitItem> fetchItems(GitItem ancestor, int fetchCount) {
		// TODO - implement GitItemProvider.fetchItems
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param predecessor
	 */
	public int getNewCount(GitItem predecessor) {
		// TODO - implement GitItemProvider.getNewCount
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param predecessor
	 */
	public List<GitItem> fetchNew(GitItem predecessor) {
		// TODO - implement GitItemProvider.fetchNew
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uri
	 * @param destination
	 * @param name
	 */
	private File cloneIfNeeded(String uri, File destination, String name) {
		// TODO - implement GitItemProvider.cloneIfNeeded
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param uri
	 * @param repositoryDir
	 */
	private Git cloneRemote(String uri, File repositoryDir) throws GitAPIException, TransportException {
		// TODO - implement GitItemProvider.cloneRemote
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param oldestItem
	 * @param fetchCount
	 */
	private List<RevCommit> readCommits(GitItem oldestItem, int fetchCount) {
		// TODO - implement GitItemProvider.readCommits
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param git
	 * @param oldestItem
	 * @param fetchCount
	 */
	private List<RevCommit> fetchPredecessors(Git git, GitItem oldestItem, int fetchCount) {
		// TODO - implement GitItemProvider.fetchPredecessors
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param latestItem
	 * @param commits
	 */
	private static int computeNewCount(GitItem latestItem, List<RevCommit> commits) {
		// TODO - implement GitItemProvider.computeNewCount
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param latestItem
	 * @param commits
	 */
	private static Optional<RevCommit> findLimiter(GitItem latestItem, List<RevCommit> commits) {
		// TODO - implement GitItemProvider.findLimiter
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param oldestItem
	 */
	private static ObjectId getId(GitItem oldestItem) {
		// TODO - implement GitItemProvider.getId
		throw new UnsupportedOperationException();
	}

}