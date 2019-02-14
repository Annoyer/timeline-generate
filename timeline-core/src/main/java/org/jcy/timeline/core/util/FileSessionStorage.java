package org.jcy.timeline.core.util;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.model.ItemSerialization;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.jcy.timeline.core.model.Memento;

public class FileSessionStorage<I extends Item> implements SessionStorage<I> {

	private static final String ITEM_SEPARATOR = "@;@@@;@";
	private final ItemSerialization<I> itemSerialization;
	private final File storageLocation;

	/**
	 *
	 * @param storageLocation
	 * @param itemSerialization
	 */
	public FileSessionStorage(File storageLocation, ItemSerialization<I> itemSerialization) {
		// TODO - implement FileSessionStorage.FileSessionStorage
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param memento
	 */
	public void store(Memento<I> memento) {
		// TODO - implement FileSessionStorage.store
		throw new UnsupportedOperationException();
	}

	public Memento<I> read() {
		// TODO - implement FileSessionStorage.read
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param memento
	 */
	private String serialize(Memento<I> memento) {
		// TODO - implement FileStorageStructure.serialize
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param memento
	 */
	private StringBuilder serializeItems(Memento<I> memento) {
		// TODO - implement FileStorageStructure.serializeItems
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param content
	 */
	private Memento<I> deserialize(String content) {
		// TODO - implement FileStorageStructure.deserialize
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param elements
	 */
	private Set deserializeItems(List<String> elements) {
		// TODO - implement FileStorageStructure.deserializeItems
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param content
	 */
	private Path writeStorageContent(String content) {
		// TODO - implement FileStorageStructure.writeStorageContent
		throw new UnsupportedOperationException();
	}

	private String readStorageContent() throws IOException {
		// TODO - implement FileStorageStructure.readStorageContent
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param file
	 */
	private static boolean isAccessibleFile(File file) {
		// TODO - implement FileStorageStructure.isAccessibleFile
		throw new UnsupportedOperationException();
	}

}