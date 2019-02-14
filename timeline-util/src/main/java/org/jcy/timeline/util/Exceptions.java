package org.jcy.timeline.util;

import java.util.concurrent.Callable;

public class Exceptions<V> {

	private Callable<V> callable;

	/**
	 *
	 * @param callable
	 */
	public static <V>Exceptions guard(Callable<V> callable) {
		// TODO - implement Exceptions.guard
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param targetType
	 */
	public <T extends RuntimeException>V with(Class<T> targetType) {
		// TODO - implement Iterables.Iterables
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param targetType
	 * @param cause
	 */
	private static <T extends Runnable>T createExceptionEnvelope(Class<T> targetType, Exception cause) {
		// TODO - implement Iterables.Iterables
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param targetType
	 * @param cause
	 */
	private static <T extends java.lang.RuntimeException>String createProblemMessage(Class<T> targetType, Exception cause) {
		// TODO - implement Iterables.Iterables
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param callable
	 */
	private Exceptions(Callable<V> callable) {
		// TODO - implement Exceptions.Exceptions
		throw new UnsupportedOperationException();
	}

}