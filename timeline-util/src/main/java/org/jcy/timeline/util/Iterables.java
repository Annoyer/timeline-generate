package org.jcy.timeline.util;

import java.util.ArrayList;
import java.util.List;

public final class Iterables {

	/**
	 * Covert the {@param iterable} into a new list.
	 *
	 * @param iterable elements.
	 */
	public static <T>List<T> asList(Iterable<T> iterable) {
		Assertion.check(iterable != null, "ITERABLE_MUST_NOT_BE_NULL");

		List<T> result = new ArrayList<>();
		for (T element : iterable) {
			result.add(element);
		}
		return result;
	}

	private Iterables() {
	}

}