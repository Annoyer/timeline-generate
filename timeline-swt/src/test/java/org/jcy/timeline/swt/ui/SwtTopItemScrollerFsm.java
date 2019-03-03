package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.jcy.timeline.swt.BackgroundThreadHelper.directUiThreadDispatcher;
import static org.jcy.timeline.swt.ui.SwtTopItemScroller.TOP_POSITION;
import static org.jcy.timeline.swt.ui.TopItemTestHelper.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class SwtTopItemScrollerFsm implements FsmModel {

    private enum State {START, INITIALIZED, UI_INVISIBLE}

    private State state;

    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private ItemUiMap<Item, Composite> itemUiMap;
    private SwtTopItemScroller<Item> scroller;
    private SwtItemUiList<Item> itemUiList;
    private Timeline<Item> timeline;
    private Shell shell;
    private SwtItemUi<Item> itemUi;

    private Item item;

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
        shell = displayHelper.createShell();
        timeline = stubTimeline();
        itemUiMap = stubUiItemMap();
        itemUiList = stubUiItemList(shell);
        scroller = spy(new SwtTopItemScroller<>(timeline, itemUiMap, itemUiList, directUiThreadDispatcher()));
        itemUi = equipItemListWithItem();
        state = State.INITIALIZED;
    }

    public boolean scrollIntoViewGuard() { return state == State.INITIALIZED; }
    @Action
    public void scrollIntoView() {
        Mockito.reset(scroller);
        itemUi.getControl().setLocation(0, TOP_POSITION + 1);
        scroller.scrollIntoView();
        verify(scroller).setScrollbarSelection(anyInt());

        Mockito.reset(scroller);
        itemUi.getControl().setLocation(0, TOP_POSITION - 1);
        scroller.scrollIntoView();
        verify(scroller, never()).setScrollbarSelection(anyInt());

        Mockito.reset(scroller);
        scroller.scrollIntoView();
        verify(scroller, never()).setScrollbarSelection(anyInt());


        Mockito.reset(scroller);
        Item item = equipWithTopItem(timeline, new Item(20L,"id") {
        });
        SwtItemUi<Item> itemUi = stubItemUi(displayHelper.createShell());
        map(itemUiMap, item, itemUi);
        scroller.scrollIntoView();
        verify(scroller, never()).setScrollbarSelection(anyInt());
    }

    public boolean resetTopItemGuard() { return state == State.INITIALIZED; }
    @Action
    public void resetTopItem() {
        item = equipWithTopItem(timeline, new Item(20L,"id") {
        });
        state = State.UI_INVISIBLE;
    }

    public boolean scrollIntoViewWithoutItemUiGuard() { return state == State.UI_INVISIBLE; }
    @Action
    public void scrollIntoViewWithoutItemUi() {
        Mockito.reset(scroller);
        scroller.scrollIntoView();
        verify(scroller, never()).setScrollbarSelection(anyInt());
    }

    public boolean scrollIntoViewIfItemUiComponentIsNotShowingGuard() { return state == State.UI_INVISIBLE; }
    @Action
    public void scrollIntoViewIfItemUiComponentIsNotShowing() {
        Mockito.reset(scroller);
        SwtItemUi<Item> itemUi = stubItemUi(displayHelper.createShell());
        map(itemUiMap, item, itemUi);

        scroller.scrollIntoView();

        verify(scroller, never()).setScrollbarSelection(anyInt());
    }

    private SwtItemUi<Item> equipItemListWithItem() {
        Item item = equipWithTopItem(timeline, new Item(20L,"id") {
        });
        ShellHelper.showShell(shell);
        SwtItemUi<Item> result = stubItemUi(newChildComposite(itemUiList.getUiRoot()));
        map(itemUiMap, item, result);
        return result;
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-top-item-scroller-fsm.dot");
    }
}

