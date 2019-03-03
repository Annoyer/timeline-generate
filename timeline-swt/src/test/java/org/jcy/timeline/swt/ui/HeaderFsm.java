package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.util.Messages;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.swt.BackgroundThreadHelper.directBackgroundProcessor;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.test.util.swt.SwtEventHelper.trigger;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HeaderFsm implements FsmModel {

    private enum State {START, INITIALIZED, BUTTON_NEW_VISIBLE}

    private State state;

    private static final int INDEX = 0;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private BackgroundProcessor backgroundProcessor;
    private Timeline<Item> timeline;
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
        timeline = mock(Timeline.class);
        backgroundProcessor = directBackgroundProcessor();
        header = createHeader(timeline, backgroundProcessor);
        state = State.INITIALIZED;
    }

    public boolean setAndGetTitleGuard() { return state == State.INITIALIZED; }
    @Action
    public void setAndGetTitle() {
        String expected = "title ";

        header.setTitle(expected);
        String actual = header.getTitle();

        assertThat(actual).isEqualTo(expected);
    }

    public boolean setTitleWithNullAsArgumentGuard() { return state == State.INITIALIZED; }
    @Action
    public void setTitleWithNullAsArgument() {
        Throwable actual = thrownBy(() -> header.setTitle(null));

        assertThat(actual)
                .hasMessage(Messages.get("TITLE_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean getControlGuard() { return state == State.INITIALIZED; }
    @Action
    public void getControl() {
        assertThat(header.getControl()).isNotNull();
    }

    public boolean updateIfNewItemIsAvailableGuard() { return state == State.INITIALIZED || state == State.BUTTON_NEW_VISIBLE; }
    @Action
    public void updateIfNewItemIsAvailable() {
        when(timeline.getNewCount()).thenReturn(1);

        header.update();

        assertThat(header.fetchNew.isVisible()).isTrue();

        state = State.BUTTON_NEW_VISIBLE;
    }

    public boolean updateIfNoNewItemIsAvailableGuard() { return state == State.INITIALIZED; }
    @Action
    public void updateIfNoNewItemIsAvailable() {
        when(timeline.getNewCount()).thenReturn(0);

        header.update();

        assertThat(header.fetchNew.isVisible()).isFalse();
    }

    public boolean onFetchNewNotificationGuard() { return state == State.BUTTON_NEW_VISIBLE; }
    @Action
    public void onFetchNewNotification() {
        ArgumentCaptor<Event> captor = forClass(Event.class);
        Listener listener = mock(Listener.class);
        header.onFetchNew(listener);

        trigger(SWT.Selection).on(header.fetchNew);

        verify(listener).handleEvent(captor.capture());
        assertThat(captor.getValue().widget).isSameAs(header.getControl());
    }

    private Header<Item> createHeader(Timeline<Item> timeline, BackgroundProcessor backgroundProcessor) {
        Header<Item> result = new Header<Item>(timeline, backgroundProcessor);
        Shell shell = displayHelper.createShell();
        result.createUi(shell);
        shell.open();
        return result;
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "header-fsm.dot");
    }
}

