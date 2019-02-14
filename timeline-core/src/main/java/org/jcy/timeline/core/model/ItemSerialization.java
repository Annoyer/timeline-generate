package org.jcy.timeline.core.model;

/**
 * Item Serialization.
 *
 * @param <I>
 */
public interface ItemSerialization<I extends Item> {

	/**
	 * Deserialize the commit item.
	 *
	 * @param input input string.
	 */
	I deserialize(String input);

	/**
	 * Serialize the commit item.
	 *
	 * @param item commit item.
	 */
	String serialize(I item);

}