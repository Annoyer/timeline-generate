package org.jcy.timeline.swt.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.ui.ItemUiMap;
import org.jcy.timeline.swt.SwtFsmTestRunner;
import org.jcy.timeline.test.util.swt.DisplayHelper;
import org.jcy.timeline.test.util.swt.SwtEventHelper;
import org.junit.Rule;
import org.junit.Test;

import static org.jcy.timeline.swt.BackgroundThreadHelper.directUiThreadDispatcher;
import static org.jcy.timeline.swt.ui.ShellHelper.showShell;
import static org.jcy.timeline.swt.ui.TopItemTestHelper.*;
import static org.mockito.Mockito.*;

public class SwtTopItemUpdaterFsm implements FsmModel {

    private enum State {START, CREATED, REGISTERED}

    private State state;
    
    @Rule
    public final DisplayHelper displayHelper = new DisplayHelper();

    private ItemUiMap<Item, Composite> itemUiMap;
    private SwtTopItemUpdater<Item> updater;
    private SwtItemUiList<Item> itemUiList;
    private Timeline<Item> timeline;
    private Shell shell;


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
        updater = spy(new SwtTopItemUpdater<>(timeline, itemUiMap, itemUiList, directUiThreadDispatcher()));

        state = State.CREATED;
    }

    public boolean registerGuard() { return state == State.CREATED; }
    @Action
    public void register() {
        updater.register();

        triggerScrollbarSelectionChange(10);

        verify(updater).update();

        state = State.REGISTERED;
    }

    public boolean updateIfBelowTopGuard() { return state == State.REGISTERED; }
    @Action
    public void updateIfBelowTop() throws Exception {
        Item item = equipItemListWithItem();
        getItemUiControl(item).setLocation(0, fromTopOffset(1));

        updater.update();

        verify(timeline).setTopItem(item);
    }


    public boolean updateIfBelowTopWithEqualTopItemGuard() { return state == State.REGISTERED; }
    @Action
    public void updateIfBelowTopWithEqualTopItem() throws Exception {
        Item item = equipItemListWithItem();
        getItemUiControl(item).setLocation(0, fromTopOffset(1));

        updater.update();

        verify(timeline).setTopItem(item);
    }

    public boolean updateIfBelowTopWithDifferentTopItemGuard() { return state == State.REGISTERED; }
    @Action
    public void updateIfBelowTopWithDifferentTopItem() throws Exception {
        Item item = equipItemListWithItem();
        equipWithTopItem(timeline, new Item(30L, "other") {
        });
        getItemUiControl(item).setLocation(0, fromTopOffset(1));

        updater.update();

        verify(timeline).setTopItem(item);
    }

    public boolean updateIfAboveTopGuard() { return state == State.REGISTERED; }
    @Action
    public void updateIfAboveTop() {
        Item item = equipItemListWithItem();
        getItemUiControl(item).setLocation(0, fromTopOffset(-1));

        updater.update();

        verify(timeline, never()).setTopItem(item);
    }

    public boolean updateWithoutItemsGuard() { return state == State.REGISTERED; }
    @Action
    public void updateWithoutItems() {
        equipWithItems(timeline, new Item( 20L, "id") {
        });

        updater.update();

        verify(timeline, never()).setTopItem(any(Item.class));
    }


    public boolean updateWithoutItemUiGuard() { return state == State.REGISTERED; }
    @Action
    public void updateWithoutItemUi() {
        updater.update();

        verify(timeline, never()).setTopItem(any(Item.class));
    }

    public boolean updateIfItemUiControlIsNotShowingGuard() { return state == State.REGISTERED; }
    @Action
    public void updateIfItemUiControlIsNotShowing() {
        Item item = equipWithItems(timeline, new Item(20L, "id") {
        });
        SwtItemUi<Item> itemUi = stubItemUi(displayHelper.createShell());
        map(itemUiMap, item, itemUi);
        getItemUiControl(item).setLocation(0, fromTopOffset(1));

        updater.update();

        verify(timeline, never()).setTopItem(item);
    }

    private Item equipItemListWithItem() {
        Item result = equipWithItems(timeline, new Item(20L, "id") {
        });
        showShell(shell);
        SwtItemUi<Item> itemUi = stubItemUi(newChildComposite(itemUiList.getUiRoot()));
        map(itemUiMap, result, itemUi);
        return result;
    }

    private Control getItemUiControl(Item item) {
        return ((SwtItemUi<Item>) itemUiMap.findByItemId(item.getId())).getControl();
    }

    private void triggerScrollbarSelectionChange(int newValue) {
        ScrollBar verticalBar = itemUiList.getUiRoot().getVerticalBar();
        verticalBar.setSelection(newValue);
        SwtEventHelper.trigger(SWT.Selection).withDetail(newValue).on(verticalBar);
    }

    private static int fromTopOffset(int i) {
        return i - SwtTopItemUpdater.TOP_OFF_SET;
    }

    @Test
    public void runTests() {
        SwtFsmTestRunner.runTest(this, "swt-top-item-updater-fsm.dot");
    }
}

