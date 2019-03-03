package org.jcy.timeline.core.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.core.model.FakeItem;
import org.jcy.timeline.core.model.Timeline;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.jcy.timeline.core.model.FakeItemUtils.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TopItemUpdaterFsm implements FsmModel {

    private enum State {UNCHANGED, CHANGED}

    private State state;

    private ItemUiMap<FakeItem, Object> itemUiMap;
    private Timeline<FakeItem> timeline;

    private FakeTopItemUpdater updater;

    private FakeItem expectItem;


    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.UNCHANGED;
        expectItem = null;
        this.clear();
    }

    private void clear() {
        timeline = stubTimeline(asArray(ALL_ITEMS));
        itemUiMap = stubItemUiMap(asArray(ALL_ITEMS));
        updater = new FakeTopItemUpdater(timeline, itemUiMap);
    }

    public boolean updateGuard() {
        return state == State.CHANGED;
    }
    @Action
    public void update() {
        updater.update();

        if (expectItem == null) {
            verify(timeline, never()).setTopItem(any(FakeItem.class));
        } else {
            verify(timeline).setTopItem(expectItem);
        }

        state = State.UNCHANGED;
        expectItem = null;
        this.clear();
    }

    public boolean changedTopItemGuard() {
        return state == State.UNCHANGED;
    }
    @Action
    public void changedTopItem() {
        when(timeline.getTopItem()).thenReturn(Optional.of(FIRST_ITEM));
        updater.setBelowTop(SECOND_ITEM, THIRD_ITEM);
        expectItem = SECOND_ITEM;
        state = State.CHANGED;
    }

    public boolean notChangedTopItemGuard() {
        return state == State.UNCHANGED;
    }
    @Action
    public void notChangedTopItem() {
        when(timeline.getTopItem()).thenReturn(Optional.of(SECOND_ITEM));
        updater.setBelowTop(SECOND_ITEM, THIRD_ITEM);
        expectItem = null;
        state = State.CHANGED;
    }

    public boolean noVisibleItemsGuard() {
        return state == State.UNCHANGED;
    }
    @Action
    public void noVisibleItems() {
        expectItem = null;
        state = State.CHANGED;
    }

    public boolean noItemUiHasBeenMappedGuard() {
        return state == State.UNCHANGED;
    }
    @Action
    public void noItemUiHasBeenMapped() {
        when(itemUiMap.findByItemId(anyString())).thenReturn(null);
        expectItem = null;
        state = State.CHANGED;
    }

    @SuppressWarnings("unchecked")
    private static Timeline<FakeItem> stubTimeline(FakeItem... fakeItems) {
        Timeline<FakeItem> result = mock(Timeline.class);
        Arrays.sort(fakeItems);
        when(result.getItems()).thenReturn(asList(fakeItems));
        when(result.getTopItem()).thenReturn(Optional.empty());
        return result;
    }

    @SuppressWarnings("unchecked")
    private static ItemUiMap<FakeItem, Object> stubItemUiMap(FakeItem... fakeItems) {
        ItemUiMap<FakeItem, Object> itemUiMap = mock(ItemUiMap.class);
        for (FakeItem fakeItem : fakeItems) {
            ItemUi<FakeItem> itemUi = mock(ItemUi.class);
            when(itemUiMap.findByItemId(fakeItem.getId())).thenReturn(itemUi);
        }
        return itemUiMap;
    }

    private FakeItem[] asArray(Set<FakeItem> allItems) {
        return allItems.toArray(new FakeItem[allItems.size()]);
    }

    private class FakeTopItemUpdater extends TopItemUpdater<FakeItem, Object> {

        private final List<ItemUi<FakeItem>> itemsBelowTop;

        FakeTopItemUpdater(Timeline<FakeItem> timeline, ItemUiMap<FakeItem, Object> itemUiMap) {
            super(timeline, itemUiMap);
            itemsBelowTop = new ArrayList<>();
        }

        public void setBelowTop(FakeItem... itemsBelowTop) {
            this.itemsBelowTop.clear();
            for (FakeItem fakeItem : itemsBelowTop) {
                this.itemsBelowTop.add(itemUiMap.findByItemId(fakeItem.getId()));
            }
        }

        @Override
        protected void register() {
        }

        @Override
        protected boolean isBelowTop(ItemUi<FakeItem> itemUi) {
            return itemsBelowTop.contains(itemUi);
        }
    }


    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "top-item-updater-fsm.dot");
    }
}
