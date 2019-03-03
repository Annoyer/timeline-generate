package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemViewer;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.swt.ui.SwtTimelineCompoundHelper.*;
import static org.jcy.timeline.swt.ui.SwtTimelineCompoundHelper.stubCompound;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

public class SwtTimelineFsm implements FsmModel {

    private enum State {START, INITIALIZED, AUTO_REFRESH_STARTED, AUTO_REFRESH_STOPPED}

    private State state;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private ItemViewer<Item, Composite> itemViewer;
    private SwtAutoUpdate<Item> autoUpdate;
    private SwtTimeline<Item> timeline;
    private Header<Item> header;
    private Shell parent;


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
    public void initialize() {
        parent = displayHelper.createShell();
        autoUpdate = stubAutoUpdate();
        itemViewer = stubItemViewer(parent);
        header = stubHeader(parent);
        timeline = new SwtTimeline<>(parent, stubCompound(header, itemViewer, autoUpdate));

        InOrder order = inOrder(header, itemViewer);
        order.verify(header).createUi(any(Composite.class));
        order.verify(itemViewer).createUi(any(Composite.class));
        order.verify(itemViewer).initialize();
        order.verify(header).onFetchNew(any(Listener.class));

        state = State.INITIALIZED;
    }

    public boolean startAutoUpdateGuard() { return state == State.INITIALIZED; }
    @Action
    public void startAutoUpdate() {
        timeline.startAutoRefresh();

        verify(autoUpdate).start();

        state = State.AUTO_REFRESH_STARTED;
    }

    public boolean stopAutoUpdateGuard() { return state == State.AUTO_REFRESH_STARTED; }
    @Action
    public void stopAutoUpdate() {
        timeline.stopAutoRefresh();

        verify(autoUpdate).stop();

        state = State.AUTO_REFRESH_STOPPED;
    }

    public boolean fetchNewGuard() { return state == State.AUTO_REFRESH_STARTED; }
    @Action
    public void fetchNew() {
        ArgumentCaptor<Listener> captor = forClass(Listener.class);
        verify(header).onFetchNew(captor.capture());

        captor.getValue().handleEvent(null);

        verify(itemViewer).fetchNew();
    }

    public boolean getComponentGuard() { return state != State.START; }
    @Action
    public void getComponent() {
        assertThat(timeline.getControl()).isNotNull();
    }

    public boolean setTitleGuard() { return state != State.START; }
    @Action
    public void setTitle() {
        String expected = "title";

        timeline.setTitle(expected);

        verify(header).setTitle(expected);
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-timeline-fsm.dot");
    }
}