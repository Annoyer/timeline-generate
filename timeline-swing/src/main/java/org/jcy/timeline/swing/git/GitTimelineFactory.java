package org.jcy.timeline.swing.git;

import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.provider.git.GitItemProvider;
import org.jcy.timeline.core.provider.git.GitItemSerialization;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.util.FileSessionStorage;
import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.swing.ui.SwingTimeline;
import org.jcy.timeline.swing.ui.SwingTimelineCompound;

import java.awt.*;
import java.io.File;

public class GitTimelineFactory {

	private final FileStorageStructure storageStructure;

	public GitTimelineFactory(FileStorageStructure storageStructure) {
		this.storageStructure = storageStructure;
	}

	/**
	 * Create the timeline.
	 *
	 * @param uri remote git repository uri.
	 * @param name git project name.
	 */
	public SwingTimeline<GitItem> create(String uri, String name) {
		return new SwingTimeline<>(
				this.createTimelineCompound(
						this.createItemProvider(uri, name), new GitItemUiFactory(), this.createStorage()
				));
	}

	SwingTimelineCompound<GitItem> createTimelineCompound(ItemProvider<GitItem> itemProvider, ItemUiFactory<GitItem, Container> itemUiFactory, SessionStorage<GitItem> sessionStorage) {
		return new SwingTimelineCompound<>(itemProvider, itemUiFactory, sessionStorage);
	}

	GitItemProvider createItemProvider(String uri, String name) {
		File timelineDirectory = storageStructure.getTimelineDirectory();
		return new GitItemProvider(uri, timelineDirectory, name);
	}

	FileSessionStorage<GitItem> createStorage() {
		File storageFile = storageStructure.getStorageFile();
		return new FileSessionStorage<>(storageFile, new GitItemSerialization());
	}
}