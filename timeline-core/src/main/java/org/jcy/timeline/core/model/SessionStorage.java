package org.jcy.timeline.core.model;

/**
 * Storing Utils to read and store commit records with a specific cache media,
 * such as file system.
 *
 * @param <I>
 */
public interface SessionStorage<I extends Item> {

	/**
	 * Write the memento into the cache storage.
	 *
	 * @param memento memento
	 */
	void store(Memento<I> memento);

	/**
	 * Read memento from the cache storage.
	 */
	Memento<I> read();

}