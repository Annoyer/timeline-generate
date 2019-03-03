package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.Messages;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.swt.ui.SwtTimelineCompoundHelper.stubHeader;
import static org.jcy.timeline.swt.ui.SwtTimelineCompoundHelper.stubItemViewer;
import static org.jcy.timeline.test.util.ThreadHelper.sleep;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.test.util.swt.DisplayHelper.flushPendingEvents;
import static org.mockito.Mockito.*;

public class SwtAutoUpdateFsm implements FsmModel {

    private enum State {START, CREATED, AUTO_UPDATE_STARTED, AUTO_UPDATE_STOP, CREATED_FAILURE}

    private State state;


    private static final int DELAY = 10;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private SwtAutoUpdate<Item> autoUpdate;
    private ItemViewer<Item, Composite> itemViewer;
    private Header<Item> header;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }


    public boolean createGuard() { return state == State.START; }
    @Action
    public void create() {
        Shell parent = displayHelper.createShell();
        header = stubHeader(parent);
        itemViewer = stubItemViewer(parent);
        autoUpdate = new SwtAutoUpdate<>(header, itemViewer, DELAY);
        state = State.CREATED;
    }

    public boolean startGuard() { return state == State.START; }
    @Action
    public void start() {
        autoUpdate.start();

        waitForDelay();

        verify(header, atLeastOnce()).update();
        verify(itemViewer, atLeastOnce()).update();

        state = State.AUTO_UPDATE_STARTED;
    }

    public boolean stopGuard() { return state == State.AUTO_UPDATE_STARTED; }
    @Action
    public void stop() {
        autoUpdate.stop();
        Mockito.reset(header, itemViewer);
        waitForDelay();

        verify(header, never()).update();
        verify(itemViewer, never()).update();

        state = State.AUTO_UPDATE_STOP;
    }

    public boolean constructWithNullAsHeaderGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsHeader() {
        Throwable actual = thrownBy(() -> new SwtAutoUpdate<>(null, itemViewer, DELAY));

        assertThat(actual)
                .hasMessage(Messages.get("HEADER_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    public boolean constructWithNullAsItemViewerGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsItemViewer() {
        Throwable actual = thrownBy(() -> new SwtAutoUpdate<>(header, null, DELAY));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_VIEWER_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    public boolean constructWithNegativeDelayGuard() { return state == State.START; }
    @Action
    public void constructWithNegativeDelay() {
        Throwable actual = thrownBy(() -> new SwtAutoUpdate<>(header, itemViewer, -1));

        assertThat(actual)
                .hasMessage(Messages.get("DELAY_MUST_NOT_BE_NEGATIVE"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    private void waitForDelay() {
        sleep(DELAY);
        flushPendingEvents();
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-auto-update-fsm.dot");
    }
}