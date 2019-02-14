package org.jcy.timeline.core.model;

import java.util.List;

/**
 * Commit Item Provider.
 *
 * @param <I>
 */
public interface ItemProvider<I extends Item> {

	/**
	 * Fetch {@param fetchCount} commits created before the {@param oldestItem} .
	 *
	 * @param ancestor ancestor.
	 * @param fetchCount the count of commit to fetch.
	 */
	List<I> fetchItems(I ancestor, int fetchCount);

	/**
	 * Get the count of commits created after the {@param predecessor}.
	 *
	 * @param predecessor
	 */
	int getNewCount(I predecessor);

	/**
	 * Get the commits created after the {@param predecessor}.
	 *
	 * @param predecessor the latest item.
	 */
	List<I> fetchNew(I predecessor);

}