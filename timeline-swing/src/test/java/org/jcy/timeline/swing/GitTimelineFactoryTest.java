package org.jcy.timeline.swing;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.swing.git.MockTimelineFactoryCreator;
import org.jcy.timeline.swing.ui.Header;
import org.jcy.timeline.swing.ui.SwingTimeline;
import org.jcy.timeline.swing.ui.SwingTopItemScroller;
import org.junit.Assert;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.nio.file.Files.readAllBytes;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.swing.TimelineStateMachine.State.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class GitTimelineFactoryTest implements FsmModel {

    private static GitTimelineFactoryTest INSTANCE = new GitTimelineFactoryTest();

    public static GitTimelineFactoryTest getInstance() {
        return INSTANCE;
    }

    private static final Logger log = LoggerFactory.getLogger(GitTimelineFactoryTest.class);

    private static final File BASE_DIRECTORY = new File(GitTimelineFactoryTest.class.getResource("/").getPath());
    private static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";
    private static final String REPOSITORY_NAME = "jenkins-web-test";
    private static final FileStorageStructure storageStructure = new FileStorageStructure(BASE_DIRECTORY);



    final TimelineStateMachine state = new TimelineStateMachine();

    private JFrame frame;

    private SwingTimeline<GitItem> swingTimeline;

    private MockTimelineFactoryCreator timelineFactoryCreator;

    private GitTimelineFactoryTest() {
    }

    @Override
    public Object getState() {
        if (state.is(RUNNING_TIME_WAITING, RUNNING_UPDATING)) {
            return state.name() + "_" + this.btnMoreState() + "_" + this.btnNewState();
        }
        return state.name();
    }

    @Override
    public void reset(boolean testing) {
        if (swingTimeline != null) {
            swingTimeline.stopAutoRefresh();
            swingTimeline = null;
        }

        if (frame != null) {
            frame.dispose();
            frame = null;
        }

        timelineFactoryCreator = null;

        while (true) {
            if (!state.is(RUNNING_UPDATING)) {
                state.reset();
                break;
            }
            log.info("Waiting before reset since backgroundProcess is doing auto update...");
        }
    }


    public boolean createTimelineGuard() {
        return state.is(START);
    }
    @Action
    public void createTimeline() {

        timelineFactoryCreator = new MockTimelineFactoryCreator(storageStructure, URI, REPOSITORY_NAME);

        SwingTimeline<GitItem> actual = timelineFactoryCreator.createTimeline();
        actual.setTitle("Title Test");
        swingTimeline = actual;

        state.setState(INITIALIZING_TIMELINE_CREATED);

        assertThat(actual).isNotNull();
        Header<GitItem> header = (Header<GitItem>) TestUtil.findFieldValue("header", actual);
        assertThat(header).isNotNull();
        assertThat(header.getTitle()).isEqualTo("Title Test");
        assertThat(cloneLocation()).exists();
        assertThat(storedMemento()).isNotEmpty();
    }

    private File cloneLocation() {
        return new File(storageStructure.getTimelineDirectory(), REPOSITORY_NAME);
    }

    private String storedMemento() {
        try {
            byte[] bytes = readAllBytes(storageStructure.getStorageFile().toPath());
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }


    public boolean showUIGuard() {
        return state.is(INITIALIZING_TIMELINE_CREATED) && swingTimeline.getComponent() != null;
    }
    @Action
    public void showUI() {
        frame = new JFrame("Timeline");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(swingTimeline.getComponent());
        frame.setBounds(100, 100, 350, 700);
        frame.setVisible(true);

        Assert.assertTrue(frame.isVisible());

        state.setState(INITIALIZING_UI_DISPLAYED);
    }

    public boolean startAutoUpdateGuard() {
        return state.is(INITIALIZING_UI_DISPLAYED) && swingTimeline != null;
    }
    @Action
    public void startAutoUpdate() {
        ItemViewer<GitItem, Container> spyItemViewer = timelineFactoryCreator.getTimelineCompound().spyItemViewer();
        Header spyHeader = timelineFactoryCreator.getTimelineCompound().spyHeader();

        state.setState(RUNNING_TIME_WAITING);
        swingTimeline.startAutoRefresh();

        try {
            Thread.sleep(5000);
            Mockito.verify(spyHeader).update();
            Mockito.verify(spyItemViewer).update();
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting auto update!", e);
        }
    }


    public boolean fetchMoreGuard() {
        return state.is(RUNNING_TIME_WAITING)
                && isBtnMoreVisible();
    }
    @Action
    public void fetchMore() {
        ItemUiList spyUiList = timelineFactoryCreator.getTimelineCompound().spyItemUiList();
        JButton fetchMore = timelineFactoryCreator.getTimelineCompound().getFetchMoreButton();
        fetchMore.doClick();

        Mockito.verify(spyUiList).fetchInBackground(FetchOperation.MORE);
    }


    public boolean fetchNewGuard() {
        return (state.is(RUNNING_TIME_WAITING, RUNNING_UPDATING));
    }

    @Action
    public void fetchNew() {
        try {
            this.mockNewCommit();
            Thread.sleep(5000);
            ItemUiList spyUiList = timelineFactoryCreator.getTimelineCompound().spyItemUiList();
            JButton fetchNew = timelineFactoryCreator.getTimelineCompound().getFetchNewButton();
            fetchNew.doClick();

            Mockito.verify(spyUiList).fetch(FetchOperation.NEW);
            Assert.assertTrue(!isBtnNewVisible());
        } catch (IOException | GitAPIException e) {
            log.error("Fail to make a new Commit.", e);
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting auto update.", e);
        }
    }

    private void mockNewCommit() throws IOException, GitAPIException {
        String time = String.valueOf(System.currentTimeMillis());
        File newfile = new File(BASE_DIRECTORY, "/.timeline/" + REPOSITORY_NAME + "/newfile" + time + ".txt");
        newfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(BASE_DIRECTORY.getCanonicalFile() + "/.timeline/" + REPOSITORY_NAME + "/.git"));

        //添加文件
        git.add().addFilepattern("newFile").call();

        git.commit().setMessage("commit at " + time).call();

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("Annoyer","jun08301230");
        git.push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }

    public boolean scrollIntoViewGuard() {
        return (state.is(RUNNING_TIME_WAITING, RUNNING_UPDATING))
                && frame.isVisible();
    }
    @Action
    public void scrollIntoView() throws InterruptedException {
        timelineFactoryCreator.getTimelineCompound().getScrollPane()
                .getVerticalScrollBar().getModel().setValue(50);

        SwingTopItemScroller<GitItem> scroller1 = timelineFactoryCreator.getTimelineCompound().spyScroller();
        scroller1.scrollIntoView();

        Thread.sleep(1000);
        verify(scroller1).setScrollbarSelection(anyInt());

        timelineFactoryCreator.getTimelineCompound().getScrollPane()
                .getVerticalScrollBar().getModel().setValue(4);

        SwingTopItemScroller<GitItem> scroller2 = timelineFactoryCreator.getTimelineCompound().spyScroller();
        scroller2.scrollIntoView();

        Thread.sleep(1000);
        verify(scroller2, never()).setScrollbarSelection(anyInt());
    }


    private String btnNewState() {
        if (isBtnNewVisible()) {
            return "BTN_NEW_VISIBLE";
        } else {
            return "BTN_NEW_INVISIBLE";
        }
    }

    private String btnMoreState() {
        if (isBtnMoreVisible()) {
            return "BTN_MORE_VISIBLE";
        } else {
            return "BTN_MORE_INVISIBLE";
        }
    }

    private boolean isBtnNewVisible() {
        return timelineFactoryCreator != null
                && timelineFactoryCreator.getTimelineCompound() != null
                && timelineFactoryCreator.getTimelineCompound().getFetchNewButton().isVisible();
    }

    private boolean isBtnMoreVisible() {
        return timelineFactoryCreator != null
                && timelineFactoryCreator.getTimelineCompound() != null
                && timelineFactoryCreator.getTimelineCompound().getFetchMoreButton().isVisible();
    }

    public static void main(String[] args) throws IOException {
        GitTimelineFactoryTest test = GitTimelineFactoryTest.getInstance();
        Tester tester = new GreedyTester(test);
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.buildGraph();
        //tester.generate(5000);
        tester.printCoverage();
    }
}
