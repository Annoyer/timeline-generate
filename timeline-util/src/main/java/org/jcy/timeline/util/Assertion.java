package org.jcy.timeline.util;

public final class Assertion {

	/**
	 * Check the condition.
	 *
	 * @param condition condition
	 * @param messagePatternName messagePatternName
	 * @param arguments arguments
	 */
	public static void check(boolean condition, String messagePatternName, Object... arguments) {
		if (!condition) {
			throw new IllegalArgumentException(formatErrorMessage(messagePatternName, arguments));
		}
	}

	/**
	 * Format the error message.
	 *
	 * @param messagePatternName messagePatternName
	 * @param arguments arguments
	 */
	public static String formatErrorMessage(String messagePatternName, Object... arguments) {
		String message = Messages.get(messagePatternName);
		check(message != null, Messages.get("MESSAGE_PATTERN_MUST_NOT_BE_NULL"));

		if (arguments != null) {
			return String.format(message, arguments);
		}
		return message;
	}

}