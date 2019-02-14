package org.jcy.timeline.core.provider.git;

import java.io.File;
import java.util.concurrent.Callable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.jcy.timeline.util.Exceptions;
import org.jcy.timeline.util.Messages;

import java.io.IOException;

class GitOperator {

	private final File repositoryLocation;

	/**
	 * Create an operation with specific git repository inferred by {@param repositoryLocation}。
	 *
	 * @param repositoryLocation location of the local git repository in file system.
	 */
	GitOperator(File repositoryLocation) {
		this.repositoryLocation = repositoryLocation;
		openRepository().close();
	}

	/**
	 * Execute the {@param gitOperation}.
	 *
	 * @param gitOperation git operation.
	 */
	<I>I execute(GitOperation<I> gitOperation) {
		Git git = openRepository();
		try {
			return guarded(() -> gitOperation.execute(git));
		} finally {
			git.close();
		}
	}

	/**
	 * Execute the callable and envelop the possiable Exception.
	 *
	 * @param callable callable.
	 */
	static <I>I guarded(Callable<I> callable) {
		return Exceptions.guard(callable).with(IllegalStateException.class);
	}

	/**
	 * Open the git repository.
	 *
	 * @return Git API.
	 */
	private Git openRepository() {
		return guarded(() -> openRepository(repositoryLocation));
	}

	/**
	 * Open the git repository.
	 *
	 * @param repositoryDir local repository location.
	 */
	private static Git openRepository(File repositoryDir) throws IOException {
		try {
			return Git.open(repositoryDir);
		} catch (RepositoryNotFoundException rnfe) {
			throw new IllegalArgumentException(Messages.get("DIRECTORY_CONTAINS_NO_GIT_REPOSITORY", repositoryDir), rnfe);
		}
	}


	@FunctionalInterface()
	interface GitOperation<I> {

		/**
		 * Execute the git operation.
		 *
		 * @param git Git API.
		 */
		I execute(Git git) throws Exception;

	}

}