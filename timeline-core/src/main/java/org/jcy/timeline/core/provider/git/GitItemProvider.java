package org.jcy.timeline.core.provider.git;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.jgit.lib.ObjectId;
import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.util.Iterables;
import org.jcy.timeline.util.Messages;

public class GitItemProvider implements ItemProvider<GitItem> {

	private final GitOperator operator;

	/**
	 * Create a new GitItemProvider using temp directory as the location of local git repository..
	 *
	 * @param uri git repository uri.
	 * @param name project name.
	 */
	public GitItemProvider(String uri, String name) {
		this(uri, new File(System.getProperty("java.io.tmpdir")), name);
	}

	/**
	 * Create a new GitItemProvider.
	 *
	 * @param uri git repository uri.
	 * @param destination directory of local git repository.
	 * @param name project name.
	 */
	public GitItemProvider(String uri, File destination, String name) {
		Assertion.check(uri != null, "URI_MUST_NOT_BE_NULL");
		Assertion.check(destination != null, "DESTINATION_MUST_NOT_BE_NULL");
		Assertion.check(name != null, "NAME_MUST_NOT_BE_NULL");
		Assertion.check(!destination.exists() || destination.isDirectory(), "DESTINATION_MUST_BE_A_DIRECTORY", destination);

		operator = new GitOperator(cloneIfNeeded(uri, destination, name));
	}

	/**
	 * Clone the remote git repository if needed.
	 *
	 * @param uri git repository uri.
	 * @param destination directory of local git repository.
	 * @param name project name.
	 */
	private File cloneIfNeeded(String uri, File destination, String name) {
		File result = new File(destination, name);
		if (!result.exists()) {
			result.mkdirs();
			GitOperator.guarded(() -> cloneRemote(uri, result)).close();
		}
		return result;
	}

	/**
	 * Clone the remote git repository.
	 *
	 * @param uri git repository uri.
	 * @param repositoryDir target directory of local git repository.
	 */
	private Git cloneRemote(String uri, File repositoryDir) throws GitAPIException {
		try {
			return Git.cloneRepository().setURI(uri).setDirectory(repositoryDir).call();
		} catch (InvalidRemoteException ire) {
			throw new IllegalArgumentException(Messages.get("URI_IS_NOT_VALID", uri));
		}
	}

	/**
	 * Read {@param fetchCount} commits created after the {@param oldestItem}
	 * If {@param oldestItem} is null, get the newest {@param fetchCount} commits.
	 *
	 * @param oldestItem the oldest item.
	 * @param fetchCount fetch count.
	 */
	private List<RevCommit> readCommits(GitItem oldestItem, int fetchCount) {
		if (oldestItem != null) {
			return operator.execute(git -> fetchPredecessors(git, oldestItem, fetchCount));
		}
		return operator.execute(git -> Iterables.asList(git.log().setMaxCount(fetchCount).call()));
	}

	/**
	 * 从git获取从 {@param oldestItem} 开始的 fetchCount+1条commit记录，包括{@param oldestItem}。
	 *
	 * @param git
	 * @param oldestItem
	 * @param fetchCount
	 */
	private List<RevCommit> fetchPredecessors(Git git, GitItem oldestItem, int fetchCount) throws Exception {
		try {
			return Iterables.asList(git.log().add(getId(oldestItem)).setMaxCount(fetchCount + 1).call());
		} catch (MissingObjectException moe) {
			File directory = git.getRepository().getDirectory();
			throw new IllegalArgumentException(Messages.get("UNKNOWN_GIT_ITEM", oldestItem, directory), moe);
		}
	}

	/**
	 * Count the commits in {@param commits} which are created after the {@param latestItem}.
	 *
	 * @param latestItem the limiter.
	 * @param commits commits collection ordered by commit time desc.
	 */
	private static int computeNewCount(GitItem latestItem, List<RevCommit> commits) {
		Optional<RevCommit> limiter = findLimiter(latestItem, commits);
		if (limiter.isPresent()) {
			return commits.indexOf(limiter.get());
		}
		return commits.size();
	}

	/**
	 * Find the {@link RevCommit} in the {@param commits} corresponding to {@param lastestItem}.
	 *
	 * @param latestItem the GitItem to find in {@param commits}
	 * @param commits commits collection ordered by commit time desc.
	 */
	private static Optional<RevCommit> findLimiter(GitItem latestItem, List<RevCommit> commits) {
		return commits
				.stream()
				.filter(commit -> commit.getId().equals(ObjectId.fromString(latestItem.getId())))
				.findFirst();
	}

	/**
	 * Get the object id from GitItem.
	 *
	 * @param oldestItem GitItem.
	 */
	private static ObjectId getId(GitItem oldestItem) {
		return ObjectId.fromString(oldestItem.getId());
	}

	/**
	 * Fetch {@param fetchCount} commits created before the {@param oldestItem} .
	 *
	 * @param ancestor ancestor.
	 * @param fetchCount the count of commit to fetch.
	 */
	public List<GitItem> fetchItems(GitItem ancestor, int fetchCount) {
		Assertion.check(fetchCount >= 0, "FETCH_COUNT_MUST_NOT_BE_NEGATIVE");

		// 拉取oldestItem开始的fetchCount+1条commits，删除oldestItem本身，封装并返回。
		// 如果oldestItem == null，拿最新的fetchCount条
		return readCommits(ancestor, fetchCount)
				.stream()
				.filter(commit -> ancestor == null || !commit.getId().equals(getId(ancestor)))
				.map(commit -> GitItem.ofCommit(commit))
				.collect(Collectors.toList());
	}

	/**
	 * Get the count of commits created after the {@param predecessor}.
	 *
	 * @param predecessor
	 */
	public int getNewCount(GitItem predecessor) {
		return fetchNew(predecessor).size();
	}

	/**
	 * Get the commits created after the {@param predecessor}.
	 *
	 * @param predecessor the latest item.
	 */
	public List<GitItem> fetchNew(GitItem predecessor) {
		Assertion.check(predecessor != null, "LATEST_ITEM_MUST_NOT_BE_NULL");

		// 拉取应用启动后的更新记录，最多100条。
		// computeNewCount计算出latestItem在commits中的位置，如果存在，只返回latestItem之后提交的commit记录。

		// 优化：拉取所有不在缓存中的，新的更新记录。
		operator.execute(git -> git.pull().call());
		List<GitItem> commits = new ArrayList<>();
		int callSize = 2;
		List<RevCommit> currentFetch = operator.execute(git -> Iterables.asList(git.log().setMaxCount(callSize).call()));
		while (!findLimiter(predecessor, currentFetch).isPresent() && currentFetch.size() > 0) {
			for (RevCommit c : currentFetch) {
				commits.add(GitItem.ofCommit(c));
			}

			currentFetch = operator.execute(git -> Iterables.asList(git.log().add(getId(commits.get(commits.size()-1))).setMaxCount(callSize+1).call()));
			if (currentFetch.size() > 0) currentFetch.remove(0);
		}

		int newCount = computeNewCount(predecessor, currentFetch);
		for (int i = 0; i < newCount; i++) {
			commits.add(GitItem.ofCommit(currentFetch.get(i)));
		}

		return commits;
	}

}