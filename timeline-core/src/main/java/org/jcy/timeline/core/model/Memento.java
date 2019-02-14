package org.jcy.timeline.core.model;

import java.util.Set;
import java.util.Optional;
import org.jcy.timeline.core.model.Item;

public class Memento<I extends Item> {

	private static Memento EMPTY_MEMENTO;
	private final Optional<I> topItem;
	private final Set<I> items;

	public Optional<I> getTopItem() {
		return this.topItem;
	}

	public Set<I> getItems() {
		return this.items;
	}

	@SuppressWarnings("unchecked")
	public static <I extends org.jcy.timeline.core.model.Item>Memento<I> empty() {
		// TODO - implement Memento.Memento
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param items
	 * @param topItem
	 */
	public Memento(Set<I> items, Optional<I> topItem) {
		// TODO - implement Memento.Memento
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param items
	 * @param topItem
	 */
	private static <I>boolean topItemExistsIfItemsNotEmpty(Set<I> items, Optional<I> topItem) {
		// TODO - implement Memento.topItemExistsIfItemsNotEmpty
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param items
	 * @param topItem
	 */
	private static <I>void topItemIsElementOfItems(Set<I> items, Optional<I> topItem) {
		// TODO - implement Memento.topItemIsElementOfItems
		throw new UnsupportedOperationException();
	}

}