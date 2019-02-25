package org.jcy.timeline.swing;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import nz.ac.waikato.modeljunit.timing.Time;
import nz.ac.waikato.modeljunit.timing.TimedFsmModel;
import nz.ac.waikato.modeljunit.timing.Timeout;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.*;
import org.jcy.timeline.core.util.FileStorageStructure;
import org.jcy.timeline.swing.git.MockTimelineFactoryCreator;
import org.jcy.timeline.swing.ui.Header;
import org.jcy.timeline.swing.ui.SwingTimeline;
import org.jcy.timeline.swing.ui.SwingTopItemScroller;
import org.jcy.timeline.swing.ui.SwingTopItemUpdater;
import org.junit.Assert;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static java.nio.file.Files.readAllBytes;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.swing.TimelineStateMachine.MainState.*;
import static org.mockito.Mockito.*;

public class TimelineSwingFsmTest implements TimedFsmModel {

    private static TimelineSwingFsmTest INSTANCE = new TimelineSwingFsmTest();

    public static TimelineSwingFsmTest getInstance() {
        return INSTANCE;
    }

    private static final Logger log = LoggerFactory.getLogger(TimelineSwingFsmTest.class);

    static final File BASE_DIRECTORY = new File(TimelineSwingFsmTest.class.getResource("/").getPath());
    static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";
    static final String REPOSITORY_NAME = "jenkins-web-test";
    private static final FileStorageStructure storageStructure = new FileStorageStructure(BASE_DIRECTORY);



    final TimelineStateMachine state = new TimelineStateMachine();

    private JFrame frame;

    private SwingTimeline<GitItem> swingTimeline;

    private MockTimelineFactoryCreator timelineFactoryCreator;

    private final TimePassed TIME_PASSED = new TimePassed();

    private static final int UPDATE_INTERVAL = 4900;

    private static final int UPDATE_TIME = 900;

    @Timeout("afterFiveSeconds") public int updateInterval;

    @Timeout("endUpdate") public int updateTime;

    @Time public int currentTime;

    private boolean isUpdateIntervalEnabled() {
        return updateInterval > 0;
    }

    private boolean isUpdateTimeEnabled() {
        return updateTime > 0;
    }

    public boolean endUpdateGuard() {
        return state.is(RUNNING) && !isUpdateIntervalEnabled() && isUpdateTimeEnabled();
    }
    @Action
    public void endUpdate() {
        while (state.isRealUpdating()) {}
        updateInterval = UPDATE_INTERVAL;
        state.setUpdating();
        currentTime = 0;
        TIME_PASSED.start();
    }

    public boolean afterFiveSecondsGuard() {
        return state.is(RUNNING) && isUpdateIntervalEnabled() && !isUpdateTimeEnabled();
    }
    @Action
    public void afterFiveSeconds() {
        while (!state.isRealUpdating()) {}
        state.setUpdating();
        updateTime = UPDATE_TIME;
        currentTime = 0;
        TIME_PASSED.start();
    }

    private TimelineSwingFsmTest() {
    }

    @Override
    public Object getState() {
        return state.getState();
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
            if (!(state.is(RUNNING) && state.isRealUpdating())) {
                state.reset();
                break;
            } else {
                try {
                    Thread.sleep(1000);
                    log.info("Waiting before reset since backgroundProcess is doing auto update...");
                } catch (InterruptedException e) {
                    // swallow
                }
            }
        }
        currentTime = 0;
        TIME_PASSED.start();
    }


    public boolean createTimelineGuard() {
        return state.is(START);
    }
    @Action
    public void createTimeline() {
        timelineFactoryCreator = new MockTimelineFactoryCreator(storageStructure, URI, REPOSITORY_NAME);

        SwingTimeline<GitItem> actual = timelineFactoryCreator.createTimeline();
        SwingTopItemScroller<GitItem> scroller = timelineFactoryCreator.getTimelineCompound().getScroller();
        actual.setTitle("Title Test");
        swingTimeline = actual;

        state.set(INITIALIZING_CREATED, false, false, false);

        assertThat(actual).isNotNull();
        Header<GitItem> header = (Header<GitItem>) TestUtil.findFieldValue("header", actual);
        assertThat(header).isNotNull();
        assertThat(header.getTitle()).isEqualTo("Title Test");
        assertThat(cloneLocation()).exists();
        assertThat(storedMemento()).isNotEmpty();

        verify(scroller).scrollIntoView();
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


    public boolean layoutUIGuard() {
        return state.is(INITIALIZING_CREATED);
    }
    @Action
    public void layoutUI() {
        frame = new JFrame("Timeline");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.add(swingTimeline.getComponent());
        frame.setBounds(100, 100, 350, 700);
        frame.setVisible(true);

        Assert.assertTrue(frame.isVisible());

        state.set(INITIALIZING_DISPLAYED, false, false, false);
    }

    public boolean startAutoUpdateGuard() {
        return state.is(INITIALIZING_DISPLAYED);
    }
    @Action
    public void startAutoUpdate() {
        state.set(RUNNING, false, this.isBtnMoreVisible(), this.isBtnNewVisible());

        ItemViewer<GitItem, Container> spyItemViewer = timelineFactoryCreator.getTimelineCompound().spyItemViewer();
        Header spyHeader = timelineFactoryCreator.getTimelineCompound().spyHeader();

        swingTimeline.startAutoRefresh();
//
        try {
            Thread.sleep(5500);
        } catch (InterruptedException e) {
            // swallow
        }
//        Mockito.verify(spyHeader).update();
//        Mockito.verify(spyItemViewer).update();
        while (state.isUpdating());
        if (state.isBtnNewVisible()) {
            fetchNew();
        }

        currentTime = 0;
        updateInterval = UPDATE_INTERVAL;
        TIME_PASSED.start();
    }


    public boolean fetchMoreGuard() {
        return state.is(RUNNING)
                && state.isBtnMoreVisible()
                && !state.isUpdating();
    }
    @Action
    public void fetchMore() {
        ItemUiList spyUiList = timelineFactoryCreator.getTimelineCompound().spyItemUiList();
        JButton fetchMore = timelineFactoryCreator.getTimelineCompound().getFetchMoreButton();
        fetchMore.doClick();

        Mockito.verify(spyUiList).fetchInBackground(FetchOperation.MORE);
    }


    public boolean fetchNewGuard() {
        return state.is(RUNNING)
                && state.isBtnNewVisible();
    }

    @Action
    public void fetchNew() {
        Assert.assertTrue(isBtnNewVisible());
        ItemUiList spyUiList = timelineFactoryCreator.getTimelineCompound().spyItemUiList();
        JButton fetchNew = timelineFactoryCreator.getTimelineCompound().getFetchNewButton();
        fetchNew.doClick();

        Mockito.verify(spyUiList).fetch(FetchOperation.NEW);
        Assert.assertTrue(!isBtnNewVisible());
        state.setBtnNewVisible(false);
    }


    public boolean updateTopItemGuard() {
        return System.currentTimeMillis() % 8 == 0
        && (state.is(INITIALIZING_DISPLAYED) ||
                state.is(RUNNING));
    }
    @Action
    public void updateTopItem() throws InterruptedException {
        SwingTopItemUpdater<GitItem> updater = timelineFactoryCreator.getTimelineCompound().spyTopItemUpdater();
        BoundedRangeModel model = timelineFactoryCreator.getTimelineCompound().getScrollPane()
                .getVerticalScrollBar().getModel();
        model.setValue(model.getValue() + 500);
        Thread.sleep(1000);
        state.setTopItemChanged(true);
    }


    boolean isBtnNewVisible() {
        return timelineFactoryCreator != null
                && timelineFactoryCreator.getTimelineCompound() != null
                && timelineFactoryCreator.getTimelineCompound().getFetchNewButton().isVisible();
    }

    boolean isBtnMoreVisible() {
        return timelineFactoryCreator != null
                && timelineFactoryCreator.getTimelineCompound() != null
                && timelineFactoryCreator.getTimelineCompound().getFetchMoreButton().isVisible();
    }

    public static void main(String[] args) throws IOException {
        TimelineSwingFsmTest test = TimelineSwingFsmTest.getInstance();
        GreedyTester tester = new GreedyTester(test);
        tester.addListener(new VerboseListener());
        tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new TransitionCoverage());
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.setResetProbability(0.005);
        GraphListener graphListener = tester.buildGraph(50000);
        //tester.generate(50000);
        tester.printCoverage();
        graphListener.printGraphDot("timeline-swing.dot");
    }

    @Override
    public int getNextTimeIncrement(Random ran) {
        return Math.max(TIME_PASSED.millisPassed() + ran.nextInt(3), 1);
    }

    private class TimePassed {
        private long current;
        private long previous;

        public void start() {
            previous = System.currentTimeMillis();
        }
        public int millisPassed() {
            current = System.currentTimeMillis();
            int result = (int) ((current - previous));
            previous = current;
            return result;
        }
    }
}
