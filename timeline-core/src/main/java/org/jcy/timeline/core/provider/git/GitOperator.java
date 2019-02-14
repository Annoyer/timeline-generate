package org.jcy.timeline.core.provider.git;

import java.io.File;
import java.util.concurrent.Callable;
import org.eclipse.jgit.api.Git;
import java.io.IOException;

class GitOperator {

	private final File repositoryLocation;

	/**
	 *
	 * @param repositoryLocation
	 */
	GitOperator(File repositoryLocation) {
		// TODO - implement GitOperator.GitOperator
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param gitOperation
	 */
	public <I>I execute(org.jcy.timeline.core.provider.git.GitOperator.GitOperation<I> gitOperation) {
		// TODO - implement GitOperator.execute
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param callable
	 */
	public <I>I guarded(Callable<I> callable) {
		// TODO - implement GitOperator.guarded
		throw new UnsupportedOperationException();
	}

	private Git openRepository() {
		// TODO - implement GitOperator.openRepository
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param repositoryDir
	 */
	private static Git openRepository(File repositoryDir) throws IOException {
		// TODO - implement GitOperator.openRepository
		throw new UnsupportedOperationException();
	}


	@FunctionalInterface()
	interface GitOperation<I> {

		/**
		 *
		 * @param git
		 */
		I execute(Git git) throws Exception;

	}

}