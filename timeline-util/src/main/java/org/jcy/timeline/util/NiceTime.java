package org.jcy.timeline.util;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public final class NiceTime {

	private static PrettyTime prettyTime = new PrettyTime(Locale.ENGLISH);

	/**
	 * Format the date into the duration util now.
	 *
	 * @param then date.
	 */
	public static String format(Date then) {
		return prettyTime.format(then);
	}

	/**
	 * Format the date. (optimize)
	 * @param thenInMillis date in mills.
	 */
	public static String format(long thenInMillis) {
		return prettyTime.format(new Date(thenInMillis));
	}

	// optimize: 静态方法就可以了。
	private NiceTime() {
	}

}