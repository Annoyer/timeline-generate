package org.jcy.timeline.core.model;

public interface SessionStorage<I extends Item> {

	/**
	 *
	 * @param memento
	 */
	void store(Memento<I> memento);

	Memento<I> read();

}