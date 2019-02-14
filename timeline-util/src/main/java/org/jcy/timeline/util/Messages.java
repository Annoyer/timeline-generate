package org.jcy.timeline.util;

import java.util.Properties;

public final class Messages {

	private static final String FILE_LOCATION = Messages.class.getResource("/").getPath() + "messages.properties";;
	private static final Properties properties = new Properties();

	/**
	 *
	 * @param patternName
	 */
	public static String get(String patternName) {
		// TODO - implement Messages.get
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param patternName
	 * @param args
	 */
	public static String get(String patternName, Object... args) {
		// TODO - implement Messages.get
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param patternName
	 * @param num
	 */
	public static String get(String patternName, long num) {
		// TODO - implement Messages.get
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param patternName
	 * @param num
	 */
	public static String get(String patternName, int num) {
		// TODO - implement Messages.get
		throw new UnsupportedOperationException();
	}

}