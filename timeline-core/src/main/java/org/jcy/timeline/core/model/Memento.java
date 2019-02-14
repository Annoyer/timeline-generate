package org.jcy.timeline.core.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import org.jcy.timeline.util.Assertion;

/**
 * The snapshot of commits in current timeline.
 * Used to communicate with the cached commit records in the file system.
 *
 * @param <I>
 */
public class Memento<I extends Item> {

	private static final Memento EMPTY_MEMENTO = new Memento<>(new HashSet<>(), Optional.empty());
	private final Optional<I> topItem;
	private final Set<I> items;

	public Optional<I> getTopItem() {
		return this.topItem;
	}

	public Set<I> getItems() {
		return this.items;
	}

	@SuppressWarnings("unchecked")
	public static <I extends Item>Memento<I> empty() {
		return (Memento<I>) EMPTY_MEMENTO;
	}

	/**
	 * The top item must be contained in the items set if both of them are exist.
	 *
	 * @param items the items set.
	 * @param topItem the top item.
	 */
	public Memento(Set<I> items, Optional<I> topItem) {
		Assertion.check(items != null, "ARGUMENT_ITEMS_MUST_NOT_BE_NULL");
		Assertion.check(topItem != null, "ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL");
		Assertion.check(topItemExistsIfItemsNotEmpty(items, topItem), "TOP_ITEM_IS_MISSING");
		Assertion.check(topItemIsElementOfItems(items, topItem), "TOP_ITEM_IS_UNRELATED");

		this.items = new HashSet<>(items);
		this.topItem = topItem;
	}

	/**
	 * Top Item Exists If Items Not Empty.
	 *
	 * @param items items to be check.
	 * @param topItem topItem to be check.
	 * @return {@code true} if the {@param items} is empty or {@param topItem} exists.
	 */
	private static <I>boolean topItemExistsIfItemsNotEmpty(Set<I> items, Optional<I> topItem) {
		return !topItem.isPresent() && items.isEmpty()
				|| topItem.isPresent() && !items.isEmpty()
				|| items.isEmpty();
	}

	/**
	 * Top item is contained in the items set if it's exists.
	 *
	 * @param items items to be check.
	 * @param topItem topItem to be check.
	 */
	private static <I>boolean topItemIsElementOfItems(Set<I> items, Optional<I> topItem) {
		return items.isEmpty() && !topItem.isPresent()
				|| !items.isEmpty() && items.contains(topItem.get());
	}

}