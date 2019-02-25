package org.jcy.timeline.swing.git;

import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.swing.ui.MockSwingTimelineCompound;
import org.jcy.timeline.swing.ui.SwingTimeline;

public class MockTimelineFactoryCreator {

    private GitTimelineFactoryProxy factoryProxy;

    private final FileStorageStructure storageStructure;

    private final String uri;

    private final String name;

    public MockTimelineFactoryCreator(FileStorageStructure storageStructure, String uri, String name) {
        this.storageStructure = storageStructure;
        this.uri = uri;
        this.name = name;
    }

    public SwingTimeline createTimeline() {
        factoryProxy = new GitTimelineFactoryProxy(storageStructure);
        return factoryProxy.create(uri, name);
    }

    public MockSwingTimelineCompound getTimelineCompound() {
        return factoryProxy.getCompound();
    }



}
