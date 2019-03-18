package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.ItemProvider;
import org.jcy.timeline.core.model.Memento;
import org.jcy.timeline.core.model.SessionStorage;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.util.Messages;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static java.lang.Thread.currentThread;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.test.util.ThreadHelper.sleep;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.test.util.swt.DisplayHelper.flushPendingEvents;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwtUiThreadDispatcherFsm implements FsmModel {

    private enum State {START, CREATED, DISPOSED, CREATE_FAILURE, DISPATCH_EXCEPTION}

    private State state;

    @Rule
    public DisplayHelper displayHelper = new DisplayHelper();

    private BackgroundProcessor backgroundProcessor;
    private SwtUiThreadDispatcher dispatcher;
    private volatile Thread backgroundThread;
    private volatile Thread uiThread;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
        displayHelper = new DisplayHelper();
        displayHelper.ensureDisplay();
    }

    @Before
    public void setUp() {
        displayHelper.ensureDisplay();
    }

    public boolean createGuard() { return state == State.START; }
    @Action
    public void create() {
        dispatcher = new SwtUiThreadDispatcher();
        backgroundProcessor = new BackgroundProcessor(dispatcher);

        state = State.CREATED;
    }

    public boolean dispatchGuard() { return state == State.CREATED; }
    @Action
    public void dispatch() {
        backgroundProcessor.process(() -> {
            backgroundThread = Thread.currentThread();
            dispatcher.dispatch(() -> executeInUiThread());
        });

        sleep(200);
        flushPendingEvents();

        assertThat(uiThread)
                .isNotNull()
                .isSameAs(currentThread())
                .isNotSameAs(backgroundThread);
    }

    public boolean dispatchIfDisplayIsDisposedGuard() { return state == State.CREATED; }
    @Action
    public void dispatchIfDisplayIsDisposed() {
        displayHelper.getDisplay().dispose();

        Throwable actual = thrownBy(() -> dispatcher.dispatch(() -> {
            throw new RuntimeException();
        }));

        assertThat(actual).isNull();

        state = State.DISPOSED;
    }

    public boolean dispatchWithNullAsRunnableGuard() { return state == State.CREATED; }
    @Action
    public void dispatchWithNullAsRunnable() {
        displayHelper.getDisplay().dispose();

        Throwable actual = thrownBy(() -> dispatcher.dispatch(() -> {
            throw new RuntimeException();
        }));

        assertThat(actual).isNull();

        state = State.DISPATCH_EXCEPTION;
    }

    public boolean constructWithNullAsDisplayGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsDisplay() {
        displayHelper.ensureDisplay();
        Throwable actual = thrownBy(() -> new SwtUiThreadDispatcher(null));

        assertThat(actual)
                .hasMessage(Messages.get("DISPLAY_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }

    private void executeInUiThread() {
        uiThread = Thread.currentThread();
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-ui-thread-dispatcher-fsm.dot");
    }
}

