package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiFactory;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.Messages;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.mockito.Mockito.mock;

public class SwtItemViewerCompoundFsm implements FsmModel {

    private enum State {START, CREATED, CREATE_FAILURE}

    private State state;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private ItemUiFactory<Item, Composite> itemUiFactory;
    private SwtItemViewerCompound<Item> compound;
    private Timeline<Item> timeline;


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
        displayHelper.ensureDisplay();
        timeline = mock(Timeline.class);
        itemUiFactory = mock(ItemUiFactory.class);
        compound = new SwtItemViewerCompound<>(timeline, itemUiFactory);

        state = State.CREATED;
    }

    public boolean getScrollerGuard() { return state == State.CREATED; }
    @Action
    public void getScroller() {
        assertThat(compound.getScroller()).isNotNull();
    }

    public boolean getItemUiListGuard() { return state == State.CREATED; }
    @Action
    public void getItemUiList() {
        assertThat(compound.getItemUiList()).isNotNull();
    }

    public boolean getTopItemUpdaterGuard() { return state == State.CREATED; }
    @Action
    public void getTopItemUpdater() {
        assertThat(compound.getTopItemUpdater()).isNotNull();
    }

    public boolean constructWithNullAsItemUiFactoryGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsItemUiFactory() {
        Throwable actual = thrownBy(() -> new SwtItemViewerCompound<>(timeline, null));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_UI_FACTORY_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }

    public boolean constructWithNullAsTimelineGuard() { return state == State.START; }
    @Action
    public void constructWithNullAsTimeline() {
        Throwable actual = thrownBy(() -> new SwtItemViewerCompound<>(null, itemUiFactory));

        assertThat(actual)
                .hasMessage(Messages.get("TIMELINE_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATE_FAILURE;
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-item-viewer-compound-fsm.dot");
    }
}
