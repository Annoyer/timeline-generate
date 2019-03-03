package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.model.*;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.BackgroundProcessor;
import org.jcy.timeline.util.Messages;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwtTimelineCompoundFsm implements FsmModel {

    private enum State {START, CREATED, CREATE_FAILURE}
    
    private State state;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private ItemUiFactory<Item, Composite> itemUiFactory;
    private SwtTimelineCompound<Item> compound;
    private SessionStorage<Item> sessionStorage;
    private ItemProvider<Item> itemProvider;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }

    @Before
    public void setUp() {
        displayHelper.ensureDisplay();
        itemProvider = mock(ItemProvider.class);
        itemUiFactory = mock(ItemUiFactory.class);
        sessionStorage = stubSessionStorage();
    }

    public boolean createGuard() { return state == State.START; }
    @Action
    public void create() {
        compound = new SwtTimelineCompound<>(itemProvider, itemUiFactory, sessionStorage);

        state = State.CREATED;
    }

    public boolean getItemViewerGuard() { return state == State.CREATED; }
    @Action
    public void getItemViewer() {
        assertThat(compound.getItemViewer()).isNotNull();
    }

    public boolean getHeaderGuard() { return state == State.CREATED; }
    @Action
    public void getHeader() {
        assertThat(compound.getHeader()).isNotNull();
    }

    public boolean getAutoUpdateGuard() { return state == State.CREATED; }
    @Action
    public void getAutoUpdate() {
        assertThat(compound.getAutoUpdate()).isNotNull();
    }

    public boolean createBackgroundProcessorGuard() { return state == State.CREATED; }
    @Action
    public void createBackgroundProcessor() {
        BackgroundProcessor actual = SwtTimelineCompound.createBackgroundProcessor();

        assertThat(actual).isNotNull();
    }

    public boolean constructWithNullAsItemProviderGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsItemProvider() {
        Throwable actual = thrownBy(() -> new SwtTimelineCompound<>(null, itemUiFactory, sessionStorage));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_PROVIDER_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }

    public boolean constructWithNullAsItemUiFactoryGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsItemUiFactory() {
        Throwable actual = thrownBy(() -> new SwtTimelineCompound<>(itemProvider, null, sessionStorage));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_UI_FACTORY_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }

    public boolean constructWithNullAsSessionStorageGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsSessionStorage() {
        Throwable actual = thrownBy(() -> new SwtTimelineCompound<>(itemProvider, itemUiFactory, null));

        assertThat(actual)
                .hasMessage(Messages.get("SESSION_STORAGE_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }


    @SuppressWarnings("rawtypes")
    private static SessionStorage stubSessionStorage() {
        SessionStorage result = mock(SessionStorage.class);
        when(result.read()).thenReturn(Memento.empty());
        return result;
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-timeline-compound-fsm.dot");
    }
}
