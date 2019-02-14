package org.jcy.timeline.core.model;

public interface ItemSerialization<I extends Item> {

	/**
	 *
	 * @param input
	 */
	I deserialize(String input);

}