package org.jcy.timeline.core.model;

import org.jcy.timeline.util.Assertion;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Timeline.
 *
 * @param <I>
 */
public class Timeline<I extends Item> {

	public static final int FETCH_COUNT_LOWER_BOUND = 1;
	public static final int FETCH_COUNT_UPPER_BOUND = 20;
	public static final int DEFAULT_FETCH_COUNT = 10;

	/**
	 * Cached commit records stored in local file system.
	 */
	private final SessionStorage<I> sessionStorage;
	/**
	 * Commit item provider to fetch commits from remote git repository.
	 */
	private final ItemProvider<I> itemProvider;
	/**
	 * Commit items in the current timeline, ordered by the commit time desc.
	 */
	private final List<I> items;
	/**
	 * Top commit item of the current timeline.
	 */
	private Optional<I> topItem;
	/**
	 * max count of commits during a fetch operation.
	 */
	private int fetchCount;


	public Timeline(ItemProvider<I> itemProvider, SessionStorage<I> sessionStorage) {
		Assertion.check(itemProvider != null, "ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL");
		Assertion.check(sessionStorage != null, "ERROR_SESSION_PROVIDER_MUST_NOT_BE_NULL");

		this.itemProvider = itemProvider;
		this.sessionStorage = sessionStorage;
		this.items = new CopyOnWriteArrayList<>();

		// 每次只拉10条
		this.fetchCount = DEFAULT_FETCH_COUNT;
		this.topItem = Optional.empty();
		// 首先读取本地文件中缓存的commit
		restore();
	}

	/**
	 * Get the copy of commit items.
	 *
	 * @return the copy of commit items.
	 */
	public List<I> getItems() {
		return new ArrayList<>(items);
	}

	/**
	 * Get the current top item in the timeline.
	 *
	 * @return the current top item
	 */
	public Optional<I> getTopItem() {
		return this.topItem;
	}

	/**
	 * Change the top item and stored a new snapshot in file system.
	 * @param topItem
	 */
	public void setTopItem(I topItem) {
		Assertion.check(topItem != null, "ERROR_TOP_ITEM_MUST_NOT_BE_NULL");
		Assertion.check(items.contains(topItem), "ERROR_TOP_ITEM_IS_UNRELATED", topItem.getId());

		this.topItem = Optional.of(topItem);
		sessionStorage.store(createMemento());
	}

	public int getFetchCount() {
		return this.fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		Assertion.check(fetchCount <= FETCH_COUNT_UPPER_BOUND, "ERROR_EXCEEDS_UPPER_BOUND", FETCH_COUNT_UPPER_BOUND, fetchCount);
		Assertion.check(fetchCount >= FETCH_COUNT_LOWER_BOUND, "ERROR_EXCEEDS_LOWER_BOUND", FETCH_COUNT_LOWER_BOUND, fetchCount);

		this.fetchCount = fetchCount;
	}

	/**
	 * 获取oldest开始的fetchCount条记录，存入内存，并写入本地文件。
	 */
	public void fetchItems() {
		addSorted(itemProvider.fetchItems(getOldest(), getFetchCount()));
		updateTopItem();
		sessionStorage.store(createMemento());
	}

	public int getNewCount() {
		return itemProvider.getNewCount(getLatest());
	}

	/**
	 * 获取latest之后的最多100条 ！最新的！ commit记录，存入内存，并写入本地文件。
	 */
	public void fetchNew() {
		addSorted(itemProvider.fetchNew(getLatest()));
		sessionStorage.store(createMemento());
	}

	/**
	 * Get the latest commit in the current timeline.
	 *
	 * @return the latest commit in the current timeline.
	 */
	private I getLatest() {
		if (items.isEmpty()) {
			return null;
		}
		return items.get(0);
	}

	/**
	 * Get the oldest commit in the current timeline.
	 *
	 * @return the oldest commit in the current timeline.
	 */
	private I getOldest() {
		if (items.isEmpty()) {
			return null;
		}
		return items.get(items.size() - 1);
	}

	/**
	 * Merge the {@param additionalItems} into the item list.
	 * And resort the item list by the timestamp desc.
	 *
	 * @param additionalItems items to be added.
	 */
	private synchronized void addSorted(List<I> additionalItems) {
		items.addAll(additionalItems);
		Collections.sort(items, (first, second) -> Long.compare(second.getTimeStamp(), first.getTimeStamp()));
	}

	/**
	 * Load the commits cached in local file system into memory.
	 */
	private void restore() {
		Memento<I> memento = sessionStorage.read();
		if (!memento.getItems().isEmpty()) {
			addSorted(new ArrayList<>(memento.getItems()));
			setTopItem(memento.getTopItem().get());
		}
	}

	/**
	 * Update the top item after fetch items from remote repository.
	 */
	private void updateTopItem() {
		if (!topItem.isPresent()) {
			topItem = items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
		}
	}

	/**
	 * Create a memento with the current items and top item in memoty.
	 *
	 * @return new memento of current memory storage.
	 */
	private Memento<I> createMemento() {
		return new Memento<>(new HashSet<>(items), getTopItem());
	}

}