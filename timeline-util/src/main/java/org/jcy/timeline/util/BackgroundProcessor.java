package org.jcy.timeline.util;

import static java.util.concurrent.Executors.defaultThreadFactory;

public class BackgroundProcessor {

	private final UiThreadDispatcher uiThreadDispatcher;
	private final java.util.concurrent.ThreadFactory threadFactory;

	public BackgroundProcessor(UiThreadDispatcher uiThreadDispatcher) {
		this.threadFactory = defaultThreadFactory();
		this.uiThreadDispatcher = uiThreadDispatcher;
	}

	/**
	 * Dispatch the task to a new thread.
	 *
	 * @param runnable task.
	 */
	public void process(Runnable runnable) {
		Assertion.check(runnable != null, "RUNNABLE_MUST_NOT_BE_NULL");

		Thread.UncaughtExceptionHandler exceptionHandler = (thread, throwable) -> dispatchToUiThread(throwable);
		Thread handler = threadFactory.newThread(runnable);
		handler.setUncaughtExceptionHandler(exceptionHandler);
		handler.start();
	}

	/**
	 * Dispatch the task to the Ui thread.
	 *
	 * @param runnable task.
	 */
	public void dispatchToUiThread(Runnable runnable) {
		uiThreadDispatcher.dispatch(runnable);
	}

	private void dispatchToUiThread(Throwable problemOnFetch) {
		dispatchToUiThread(() -> rethrow(problemOnFetch));
	}

	private void rethrow(Throwable problemOnFetch) {
		if (problemOnFetch instanceof Error) {
			throw (Error) problemOnFetch;
		}
		throw (RuntimeException) problemOnFetch;
	}

}