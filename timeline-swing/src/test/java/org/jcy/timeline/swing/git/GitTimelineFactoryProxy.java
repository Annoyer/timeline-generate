package org.jcy.timeline.swing.git;

import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.swing.ui.MockSwingTimelineCompound;
import org.jcy.timeline.swing.ui.SwingTimelineCompound;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GitTimelineFactoryProxy extends GitTimelineFactory {

    private MockSwingTimelineCompound compound;

    GitTimelineFactoryProxy(FileStorageStructure storageStructure) {
        super(storageStructure);
    }

    @Override
    SwingTimelineCompound<GitItem> createTimelineCompound(ItemProvider<GitItem> itemProvider, ItemUiFactory<GitItem, Container> itemUiFactory, SessionStorage<GitItem> sessionStorage) {
        SwingTimelineCompound<GitItem> real = super.createTimelineCompound(itemProvider, itemUiFactory, sessionStorage);
        assertThat(real).isNotNull();
        compound = new MockSwingTimelineCompound(itemProvider, itemUiFactory, sessionStorage);
        return compound;
    }

    MockSwingTimelineCompound getCompound() {
        return this.compound;
    }
}
