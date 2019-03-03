package org.jcy.timeline.core.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.core.model.FakeItem;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.util.Messages;
import org.junit.Test;
import org.mockito.Mockito;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.core.model.FakeItemUtils.FIRST_ITEM;
import static org.mockito.Mockito.*;

public class ItemUiMapWithFetchOperationFsm implements FsmModel {

    private enum State { START, CREATED, CREATE_FAILURE, TIMELINE_CHECKED, ITEMS_PREPARED};

    private State state;

    private static final int ITEM_INDEX = 0;

    private ItemUiMap<FakeItem, Object> target;
    private Timeline<FakeItem> timeline;
    private ItemUi<FakeItem> itemUi;
    private Object uiContext;
    private FakeItem item;

    private int fetchMoreCount;
    private int fetchNewCount;

    private boolean isOriginalTimelineEmpty = false;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
        fetchMoreCount = 0;
        fetchNewCount = 0;
    }

    public boolean createGuard() {
        return state == State.START;
    }
    @Action
    public void create() {
        uiContext = new Object();
        item = FIRST_ITEM;
        timeline = stubTimeline(item);
        itemUi = mock(ItemUi.class);
        target = new ItemUiMap<>(timeline, stubItemUiFactory(itemUi, uiContext, item, ITEM_INDEX));
        state = State.CREATED;
        isOriginalTimelineEmpty = !isOriginalTimelineEmpty;
    }

    public boolean createWithNullAsTimelineGuard() {
        return state == State.START;
    }
    @Action
    public void createWithNullAsTimeline() {
        Throwable actual = thrownBy(() -> new ItemUiMap<>(null, stubItemUiFactory(null, null, null, 0)));

        assertThat(actual)
                .hasMessage(Messages.get("TIMELINE_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
        state = State.CREATE_FAILURE;
    }

    public boolean createWithNullAsItemUiFactoryGuard() {
        return state == State.START;
    }
    @Action
    public void createWithNullAsItemUiFactory() {
        Throwable actual = thrownBy(() -> new ItemUiMap<>(timeline, null));

        assertThat(actual)
                .hasMessage(Messages.get("ITEM_UI_FACTORY_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
        state = State.CREATE_FAILURE;
    }

    public boolean isTimelineEmptyGuard() {
        return state == State.CREATED;
    }
    @Action
    public void isTimelineEmpty() {
        if (isOriginalTimelineEmpty) {
            equipTimeline(timeline);
        } else {
            equipTimeline(timeline, item);
        }

        boolean actual = target.isTimelineEmpty();

        assertThat(actual).isEqualTo(isOriginalTimelineEmpty);

        state = State.TIMELINE_CHECKED;
    }

    public boolean fetchAndUpdateGuard() {
        return state == State.TIMELINE_CHECKED && isOriginalTimelineEmpty;
    }
    @Action
    public void fetchAndUpdate() {
        this.fetch(FetchOperation.MORE);
        equipTimeline(timeline, item);
        this.update();

        state = State.ITEMS_PREPARED;
    }

    public boolean fetchGuard() {
        return state == State.ITEMS_PREPARED;
    }
    @Action
    public void fetch() {
        this.fetch(FetchOperation.NEW);
    }

    private void fetch(FetchOperation operation) {
        target.fetch(operation);
        switch (operation) {
            case NEW: verify(timeline, times(++fetchNewCount)).fetchNew(); break;
            case MORE:verify(timeline, times(++fetchMoreCount)).fetchItems();break;
        }
    }


    public boolean updateGuard() {
        return state == State.ITEMS_PREPARED ||
                (state == State.TIMELINE_CHECKED && !isOriginalTimelineEmpty);
    }
    @Action
    public void update() {
        target.update(uiContext);
        ItemUi<FakeItem> actual = target.findByItemId(item.getId());
        assertThat(actual).isEqualTo(itemUi);

        if (state == State.ITEMS_PREPARED) {
            verify(actual).update();
            Mockito.reset(actual);
        } else {
            verify(actual, never()).update();
        }

        state = State.ITEMS_PREPARED;
    }

    public boolean findByItemIdGuard() {
        return state == State.ITEMS_PREPARED;
    }
    @Action
    public void findByItemId() {
        assertThat(target.findByItemId(item.getId())).isSameAs(itemUi);
        assertThat(target.findByItemId("UNKNOWN")).isNull();
    }

    public boolean findByItemIdWithNullIdGuard() {
        return state == State.ITEMS_PREPARED;
    }
    @Action
    public void findByItemIdWithNullId() {
        Throwable actual = thrownBy(() -> target.findByItemId(null));

        assertThat(actual)
                .hasMessageContaining(Messages.get("ID_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean containsItemUiGuard() {
        return state == State.ITEMS_PREPARED;
    }
    @Action
    public void containsItemUi() {
        assertThat(target.containsItemUi(item.getId())).isTrue();
        assertThat(target.containsItemUi("UNKNOWN")).isFalse();
    }

    public boolean containsItemUiWithNullIdGuard() {
        return state == State.ITEMS_PREPARED;
    }
    @Action
    public void containsItemUiWithNullId() {
        Throwable actual = thrownBy(() -> target.containsItemUi(null));

        assertThat(actual)
                .hasMessageContaining(Messages.get("ID_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static ItemUiFactory<FakeItem, Object> stubItemUiFactory(
            ItemUi<FakeItem> expected, Object uiContext, FakeItem item, int itemIndex) {
        @SuppressWarnings("unchecked")
        ItemUiFactory<FakeItem, Object> result = mock(ItemUiFactory.class);
        when(result.create(uiContext, item, itemIndex)).thenReturn(expected);
        return result;
    }

    private static Timeline<FakeItem> stubTimeline(FakeItem... items) {
        @SuppressWarnings("unchecked")
        Timeline<FakeItem> result = mock(Timeline.class);
        equipTimeline(result, items);
        return result;
    }

    private static void equipTimeline(Timeline<FakeItem> timeline, FakeItem... items) {
        when(timeline.getItems()).thenReturn(asList(items));
    }

    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "item-ui-map-with-fetch-operation-fsm.dot");
    }
}
