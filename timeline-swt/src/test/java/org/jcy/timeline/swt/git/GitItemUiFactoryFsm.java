package org.jcy.timeline.swt.git;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.swt.ui.SwtItemUi;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.Messages;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;

public class GitItemUiFactoryFsm implements FsmModel {

    private enum State {START, INITIALIZED}

    private State state;

    private static final int INDEX = 0;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private GitItemUiFactory factory;
    private Composite uiContext;
    private GitItem gitItem;

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
        factory = new GitItemUiFactory();
        uiContext = displayHelper.createShell();
        gitItem = new GitItem("id", currentTimeMillis(), "author", "content");
        state = State.INITIALIZED;
    }

    public boolean createWithNullAsItemGuard() { return state == State.INITIALIZED; }
    @Action
    public void createWithNullAsItem() {
        Throwable actual = thrownBy(() -> factory.create(uiContext, null, INDEX));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean createWithNegativeIndexGuard() { return state == State.INITIALIZED; }
    @Action
    public void createWithNegativeIndex() {
        Throwable actual = thrownBy(() -> factory.create(uiContext, gitItem, -1));

        assertThat(actual)
                .hasMessage(Messages.get("INDEX_MUST_NOT_BE_NEGATIVE"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean createWithNullAsUiContextGuard() { return state == State.START; }
    @Action
    public void createWithNullAsUiContext() {
        Throwable actual = thrownBy(() -> factory.create(null, gitItem, INDEX));

        assertThat(actual)
                .hasMessage(Messages.get("UI_CONTEXT_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean createGuard() { return state == State.START; }
    @Action
    public void create() throws IOException {
        SwtItemUi<GitItem> actual = (SwtItemUi<GitItem>) factory.create(uiContext, gitItem, INDEX);

        assertThat(actual.getControl()).isSameAs(uiContext.getChildren()[INDEX]);
    }


    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "git-item-ui-factory-fsm.dot");
    }
}
