package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.util.BackgroundProcessor;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.ui.FetchOperation.MORE;
import static org.jcy.timeline.swt.BackgroundThreadHelper.directBackgroundProcessor;
import static org.jcy.timeline.test.util.swt.SwtEventHelper.trigger;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class SwtItemUiListFsm implements FsmModel {

    private enum State {START, INITIALIZED}

    private State state;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private BackgroundProcessor backgroundProcessor;
    private ItemUiMap<Item, Composite> itemUiMap;
    private SwtItemUiList<Item> itemUiList;

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
        itemUiMap = mock(ItemUiMap.class);
        backgroundProcessor = directBackgroundProcessor();
        itemUiList = new SwtItemUiList<>(itemUiMap, backgroundProcessor);
        itemUiList.createUi(displayHelper.createShell());

        assertThat(itemUiList.getUiRoot()).isNotNull();
        assertThat(itemUiList.getContent()).isNotNull();

        state = State.INITIALIZED;
    }

    public boolean fetchMoreGuard() { return state == State.INITIALIZED; }
    @Action
    public void fetchMore() throws IOException {

        trigger(SWT.Selection).on(itemUiList.fetchMore);

        InOrder order = inOrder(itemUiMap, backgroundProcessor);
        order.verify(itemUiMap).fetch(MORE);
        order.verify(itemUiMap).update(itemUiList.content);
    }


    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-item-ui-list-fsm.dot");
    }
}
