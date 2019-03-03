package org.jcy.timeline.core.util;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.util.Messages;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.core.util.FileStorageStructure.STORAGE_FILE;
import static org.jcy.timeline.core.util.FileStorageStructure.TIMELINE_DIRECTORY;

/**
 * Test with different input in different action leading to different states.
 */
public class FileStorageStructureFsm implements FsmModel {

    private enum State { UNCREATED, CREATED_SUCCESS, CREATED_FAILURE }

    private State state;

    private TemporaryFolder temporaryFolder;
    private FileStorageStructure storageStructure;
    private File timelineDirectory;
    private File baseDirectory;
    private File storageFile;

    public boolean createGuard() {
        return state == State.UNCREATED;
    }
    @Action
    public void create() throws IOException {
        temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        baseDirectory = temporaryFolder.newFolder();
        storageStructure = new FileStorageStructure(baseDirectory);
        timelineDirectory = createCanonicalFile(baseDirectory, TIMELINE_DIRECTORY);
        storageFile = createCanonicalFile(timelineDirectory, STORAGE_FILE);

        assertThat(timelineDirectory).doesNotExist();
        assertThat(storageFile).doesNotExist();

        state = State.CREATED_SUCCESS;
    }

    public boolean createWithInvalidFileNameGuard() {
        return state == State.UNCREATED;
    }
    @Action
    public void createWithInvalidFileName() throws IOException {
        Throwable actual = thrownBy(() -> new FileStorageStructure(new File("?<>% *:|")));

        assertThat(actual)
                .isInstanceOf(IllegalArgumentException.class)
                .hasCauseInstanceOf(IOException.class);

        state = State.CREATED_FAILURE;
    }

    public boolean createWithNullAsArgumentGuard() {
        return state == State.UNCREATED;
    }
    @Action
    public void createWithNullAsArgument() throws IOException {
        Throwable actual = thrownBy(() -> new FileStorageStructure(null));

        assertThat(actual)
                .hasMessage(Messages.get("BASE_DIRECTORY_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    public boolean getStorageFileGuard() {
        return state == State.CREATED_SUCCESS;
    }
    @Action
    public void getStorageFile() throws IOException {
        File actual = storageStructure.getStorageFile();
        assertThat(actual)
                .isEqualTo(storageFile)
                .exists();
    }

    public boolean getTimelineDirectoryGuard() {
        return state == State.CREATED_SUCCESS;
    }
    @Action
    public void getTimelineDirectory() {
        File actual = storageStructure.getTimelineDirectory();

        assertThat(actual)
                .isEqualTo(timelineDirectory)
                .exists();
    }

    private static File createCanonicalFile(File baseDirectory, String fileName) throws IOException {
        return new File(baseDirectory, fileName).getCanonicalFile();
    }

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.UNCREATED;
        if (temporaryFolder != null) {
            temporaryFolder.delete();
            temporaryFolder = null;
        }
    }

    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "file-storage-structure-fsm.dot");
    }
}
