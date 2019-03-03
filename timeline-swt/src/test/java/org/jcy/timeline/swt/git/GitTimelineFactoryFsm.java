package org.jcy.timeline.swt.git;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.swt.ui.SwtTimeline;
import org.jcy.timeline.test.util.GitRepository;
import org.jcy.timeline.test.util.GitRule;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class GitTimelineFactoryFsm implements FsmModel {

    private static final String CLONE_NAME = "clone";

    private enum State {START, INITIALIZED}

    private State state;

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();
    @Rule
    public final GitRule gitRule = new GitRule();

    private FileStorageStructure storageStructure;
    private String remoteRepositoryUri;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }

    public boolean initializeGuard() { return state == State.START; }
    @Action
    public void initialize() throws IOException {
        File baseDirectory = temporaryFolder.newFolder();
        storageStructure = new FileStorageStructure(baseDirectory);
        remoteRepositoryUri = createRepository(baseDirectory);
        state = State.INITIALIZED;
    }

    public boolean createTimelineGuard() {return state == State.INITIALIZED; }
    @Action
    public void createTimeline() throws IOException {
        GitTimelineFactory factory = new GitTimelineFactory(storageStructure);
        Shell parent = displayHelper.createShell();

        SwtTimeline<GitItem> actual = factory.create(parent, remoteRepositoryUri, CLONE_NAME);

        assertThat(actual).isNotNull();
        assertThat(cloneLocation()).exists();
        assertThat(storedMemento()).isNotEmpty();
    }

    private File cloneLocation() {
        return new File(storageStructure.getTimelineDirectory(), CLONE_NAME);
    }

    private String storedMemento() throws IOException {
        byte[] bytes = readAllBytes(storageStructure.getStorageFile().toPath());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String createRepository(File baseDirectory) throws IOException {
        File remoteRepositoryLocation = new File(baseDirectory, "repository.git");
        GitRepository remote = gitRule.create(remoteRepositoryLocation);
        remote.commitFile("file", "content", "message");
        return remoteRepositoryLocation.toURI().toString();
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "git-timeline-factory-fsm.dot");
    }
}
