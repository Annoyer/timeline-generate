package org.jcy.timeline.core.util;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.model.ItemSerialization;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jcy.timeline.core.model.Memento;

import org.jcy.timeline.util.Assertion;
import org.jcy.timeline.util.Exceptions;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Arrays.asList;
import static org.jcy.timeline.util.Exceptions.guard;

/**
 * Storing utils communicating with the file system.
 *
 * @param <I>
 */
public class FileSessionStorage<I extends Item> implements SessionStorage<I> {

	private static final String ITEM_SEPARATOR = "@;@@@;@";
	private final ItemSerialization<I> itemSerialization;
	private final File storageLocation;

	/**
	 * Create a new file session storage.
	 *
	 * @param storageLocation the location to store in the file system.
	 * @param itemSerialization serialization util.
	 */
	public FileSessionStorage(File storageLocation, ItemSerialization<I> itemSerialization) {
		Assertion.check(storageLocation != null, "STORAGE_LOCATION_MUST_NOT_BE_NULL");
		Assertion.check(itemSerialization != null, "ITEM_SERIALIZATION_MUST_NOT_BE_NULL", itemSerialization);
		Assertion.check(isAccessibleFile(storageLocation), "STORAGE_LOCATION_IS_NOT_ACCESSIBLE", storageLocation);

		this.storageLocation = storageLocation;
		this.itemSerialization = itemSerialization;
	}

	public void store(Memento<I> memento) {
		String storageContent = serialize(memento);
		Exceptions.guard(() -> writeStorageContent(storageContent)).with(IllegalStateException.class);

	}

	public Memento<I> read() {
		String storageContent = guard(() -> readStorageContent()).with(IllegalStateException.class);
		return deserialize(storageContent);
	}

	/**
	 * Serialize Items and the top item in {@param memento}.
	 *
	 * @param memento memento.
	 */
	private String serialize(Memento<I> memento) {
		StringBuilder result = new StringBuilder();
		if (!memento.getItems().isEmpty()) {
			result.append(serializeItems(memento));
			result.append(itemSerialization.serialize(memento.getTopItem().orElse(null)));
		}
		return result.toString();
	}

	/**
	 * Serialize Items in {@param memento}.
	 *
	 * @param memento memento.
	 */
	private StringBuilder serializeItems(Memento<I> memento) {
		StringBuilder result = new StringBuilder();
		memento.getItems().forEach(item -> result.append(itemSerialization.serialize(item)).append(ITEM_SEPARATOR));
		return result;
	}

	/**
	 * Deserialize the string into a {@link Memento}.
	 *
	 * @param content input string.
	 */
	private Memento<I> deserialize(String content) {
		if (content.isEmpty()) {
			return Memento.empty();
		}
		List<String> elements = new ArrayList<String>(asList(content.split(ITEM_SEPARATOR)));
		String topItemElement = elements.remove(elements.size() - 1);
		Set<I> items = deserializeItems(elements);
		I topItem = itemSerialization.deserialize(topItemElement);
		return new Memento<>(items, Optional.of(topItem));
	}

	/**
	 * Deserialize the Items.
	 *
	 * @param elements items to deserialize.
	 */
	private Set deserializeItems(List<String> elements) {
		return elements.stream().map(element -> itemSerialization.deserialize(element)).collect(Collectors.toSet());
	}

	/**
	 * Read the {@param content} into file system.
	 *
	 * @param content content to store.
	 */
	private Path writeStorageContent(String content) throws IOException {
		return Files.write(storageLocation.toPath(), content.getBytes(UTF_8), WRITE, TRUNCATE_EXISTING);
	}

	/**
	 * Read the storage from file system.
	 */
	private String readStorageContent() throws IOException {
		return new String(Files.readAllBytes(storageLocation.toPath()), UTF_8);
	}

	/**
	 * If the {@param file} is accessible.
	 *
	 * @param file file
	 */
	private static boolean isAccessibleFile(File file) {
		return file.exists() && file.canRead() && file.canWrite() && file.isFile();
	}

}