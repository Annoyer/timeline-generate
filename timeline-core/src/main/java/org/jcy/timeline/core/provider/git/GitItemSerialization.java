package org.jcy.timeline.core.provider.git;

import org.jcy.timeline.core.model.ItemSerialization;
import org.jcy.timeline.util.Assertion;

public class GitItemSerialization implements ItemSerialization<GitItem> {

	private static final String SEPARATOR = "@;@;@;@";

	/**
	 * Deserialize the GitItem.
	 *
	 * @param input input string.
	 */
	public GitItem deserialize(String input) {
		Assertion.check(input != null, "INPUT_MUST_NOT_BE_NULL");

		String[] split = input.split(SEPARATOR);
		return new GitItem(split[0], Long.parseLong(split[1]), split[2], split[3]);
	}

	/**
	 * Serialize the GitItem.
	 *
	 * @param item item
	 */
	public String serialize(GitItem item) {
		Assertion.check(item != null, "ITEM_MUST_NOT_BE_NULL");

		return new StringBuilder()
				.append(item.getId()).append(SEPARATOR)
				.append(item.getTimeStamp()).append(SEPARATOR)
				.append(item.getAuthor()).append(SEPARATOR)
				.append(item.getContent()).toString();
	}

}