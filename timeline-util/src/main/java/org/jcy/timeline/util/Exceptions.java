package org.jcy.timeline.util;

import java.util.concurrent.Callable;

import static java.lang.String.format;

public final class Exceptions<V> {

	private Callable<V> callable;

	/**
	 * Wrap the {@param callable}.
	 *
	 * @param callable callable.
	 */
	public static <V>Exceptions<V> guard(Callable<V> callable) {
		return new Exceptions<>(callable);
	}

	/**
	 * Execute the task and wrap the possible exception.
	 *
	 * @param targetType wrapping type of exception.
	 */
	public <T extends RuntimeException>V with(Class<T> targetType) {
		try {
			return callable.call();
		} catch (RuntimeException rte) {
			throw rte;
		} catch (Exception cause) {
			throw createExceptionEnvelope(targetType, cause);
		}
	}

	/**
	 * Wrap the exception into {@param targetType}.
	 *
	 * @param targetType wrapping type.
	 * @param cause actual exception.
	 */
	private static <T extends RuntimeException>T createExceptionEnvelope(Class<T> targetType, Exception cause) {
		try {
			return targetType.getConstructor(Throwable.class).newInstance(cause);
		} catch (Exception e) {
			throw new IllegalArgumentException(createProblemMessage(targetType, cause), e);
		}
	}

	private static <T extends RuntimeException>String createProblemMessage(Class<T> targetType, Exception cause) {
		return format("Target exception type <%s> cannot be instanciated to defuse checked exception <%s[%s]>.",
				targetType.getName(),
				cause.getClass().getName(),
				cause.getMessage());
	}

	private Exceptions(Callable<V> callable) {
		this.callable = callable;
	}

}