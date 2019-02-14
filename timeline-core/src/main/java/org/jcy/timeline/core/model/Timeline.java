package org.jcy.timeline.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Timeline<I extends Item> {

	public static final int FETCH_COUNT_LOWER_BOUND = 1;
	public static final int FETCH_COUNT_UPPER_BOUND = 20;
	public static final int DEFAULT_FETCH_COUNT = 10;
	private final SessionStorage<I> sessionStorage;
	private final ItemProvider<I> itemProvider;
	private final List<I> items;
	private Optional<I> topItem;
	private int fetchCount;

	public Timeline(ItemProvider<I> itemProvider, SessionStorage<I> sessionStorage) {
		this.itemProvider = itemProvider;
		this.sessionStorage = sessionStorage;
		this.items = new ArrayList<>();
	}

	public List<I> getItems() {
		return this.items;
	}

	public Optional<I> getTopItem() {
		return this.topItem;
	}

	public void setTopItem(Optional<I> topItem) {
		this.topItem = topItem;
	}

	public int getFetchCount() {
		return this.fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}

	public void fetchItems() {
		// TODO - implement Timeline.fetchItems
		throw new UnsupportedOperationException();
	}

	public int getNewCount() {
		// TODO - implement Timeline.getNewCount
		throw new UnsupportedOperationException();
	}

	public void fetchNew() {
		// TODO - implement Timeline.fetchNew
		throw new UnsupportedOperationException();
	}

	private I getLatest() {
		// TODO - implement Timeline.getLatest
		throw new UnsupportedOperationException();
	}

	private I getOldest() {
		// TODO - implement Timeline.getOldest
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param additionalItems
	 */
	private void addSorted(List<I> additionalItems) {
		// TODO - implement Timeline.addSorted
		throw new UnsupportedOperationException();
	}

	private void restore() {
		// TODO - implement Timeline.restore
		throw new UnsupportedOperationException();
	}

	private void updateTopItem() {
		// TODO - implement Timeline.updateTopItem
		throw new UnsupportedOperationException();
	}

	private Memento<I> createMemento() {
		// TODO - implement Timeline.createMemento
		throw new UnsupportedOperationException();
	}

}