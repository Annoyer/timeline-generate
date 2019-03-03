package org.jcy.timeline.core.model;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.util.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.core.model.FakeItemUtils.*;
import static org.jcy.timeline.core.model.FakeItemUtils.FIRST_ITEM;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

public class TimelineFsm implements FsmModel {

    private static final int START_ID = 500000;

    private SessionStorage<FakeItem> sessionStorage;

    private SessionStorage<FakeItem> sessionStorage123;
    private FakeItemProviderStub itemProvider;
    private Timeline<FakeItem> timeline;

    private enum State {
        START, SESSION_STORAGE_CREATED, CREATED, CREATED_FAILURE
    }

    private State state = State.START;

    private boolean hasNew;
    private int currentStorageCall;
    private int maxItemId;
    private int minItemId;
    private FakeItem newItemToFetch;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
        hasNew = false;
        currentStorageCall = 0;
        maxItemId = START_ID - 1;
        minItemId = START_ID;

        sessionStorage = null;
        itemProvider = null;
        timeline = null;
        newItemToFetch = null;
        sessionStorage123 = stubSessionStorage(FIRST_ITEM, SECOND_ITEM, FIRST_ITEM);
    }

    public boolean createSessionStorageGuard() {
        return state == State.START;
    }
    @Action
    public void createSessionStorage() throws IOException {
        sessionStorage = stubSessionStorage();

        state = State.SESSION_STORAGE_CREATED;
    }

    public boolean createGuard() {
        return state == State.SESSION_STORAGE_CREATED;
    }
    @Action
    public void create() {
        itemProvider = new FakeItemProviderStub();

        this.mementoCreateAssertionCheck();

        Timeline<FakeItem> actual = new Timeline<>(itemProvider, sessionStorage123);
        assertThat(actual.getItems()).containsExactly(SECOND_ITEM, FIRST_ITEM);
        assertThat(actual.getTopItem()).contains(FIRST_ITEM);

        timeline = new Timeline<>(itemProvider, sessionStorage);

        timeline.fetchItems();
        ++currentStorageCall;

        state = State.CREATED;
    }

    public boolean createWithNullAsItemProviderGuard() {
        return state == State.SESSION_STORAGE_CREATED;
    }
    @Action
    public void createWithNullAsItemProvider() {
        assertThat(thrownBy(() -> new Timeline<>(null, sessionStorage)))
                .hasMessage(Messages.get("ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    public boolean createWithNullAsSessionStorageGuard() {
        return state == State.SESSION_STORAGE_CREATED;
    }
    @Action
    public void createWithNullAsSessionStorage() {
        assertThat(thrownBy(() -> new Timeline<>(null, sessionStorage)))
                .hasMessage(Messages.get("ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        state = State.CREATED_FAILURE;
    }

    private void mementoCreateAssertionCheck() {
        assertThat(thrownBy(() -> new Memento<>(null, Optional.empty())))
                .hasMessage(Messages.get("ARGUMENT_ITEMS_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);

        Set<FakeItem> items = new HashSet<>();
        assertThat(thrownBy(() -> new Memento<>(items, null)))
                .hasMessage(Messages.get("ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
        Memento<FakeItem> memento = new Memento<>(items, Optional.empty());
        FakeItem item = new FakeItem("0", 0);
        items.add(item);
        assertThat(memento.getItems()).doesNotContain(item);

        assertThat(thrownBy(() -> new Memento<>(items, Optional.empty())))
                .hasMessage(Messages.get("TOP_ITEM_IS_MISSING"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(thrownBy(() -> new Memento<>(items, Optional.of(new FakeItem("1",1)))))
                .hasMessage(Messages.get("TOP_ITEM_IS_UNRELATED"))
                .isInstanceOf(IllegalArgumentException.class);
    }


    public boolean fetchItemsGuard() {
        return state == State.CREATED;
    }
    @Action
    public void fetchItems() {
        int count = new Random().nextInt(20);
        FakeItem[] items = this.createMoreItems(count);

        List<FakeItem> fetched = new ArrayList<>();
        List<FakeItem> unfetched = new ArrayList<>();
        for (int i = items.length-1; i >= 0; i--) {
            if (fetched.size() < 10) {
                fetched.add(items[i]);
            } else {
                unfetched.add(items[i]);
            }
        }
        itemProvider.addItems(items);
        timeline.fetchItems();
        Assert.assertTrue(FakeItemUtils.containsAll(fetched, timeline.getItems()));
        Assert.assertTrue(FakeItemUtils.containsNone(unfetched, timeline.getItems()));
        MementoAssert.assertThat(captureStorageMemento(++currentStorageCall))
                .isEqualTo(this.createMemento(timeline.getTopItem().orElse(null), timeline.getItems()));

        while (!FakeItemUtils.containsAll(unfetched, timeline.getItems())) {
            timeline.fetchItems();
            ++currentStorageCall;
        }
    }

    public boolean getNewCountGuard() {
        return state == State.CREATED && !hasNew;
    }
    @Action
    public void getNewCount() {
        timeline.fetchItems();
        ++currentStorageCall;

        int actual = timeline.getNewCount();

        assertThat(actual).isEqualTo(0);

        FakeItem[] newItems = this.createNewItems(2);
        itemProvider.addItems(newItems[0]);
        timeline.fetchNew();
        ++currentStorageCall;

        assertThat(timeline.getNewCount()).isEqualTo(0);
        itemProvider.addItems(newItems[1]);
        assertThat(timeline.getNewCount()).isEqualTo(1);
        newItemToFetch = newItems[1];
        hasNew = true;
    }

    public boolean fetchNewGuard() {
        return state == State.CREATED && hasNew;
    }
    @Action
    public void fetchNew() {
        timeline.fetchNew();

        assertThat(timeline.getItems()).contains(newItemToFetch);
        MementoAssert.assertThat(captureStorageMemento(++currentStorageCall))
                .hasItems(newItemToFetch);
        hasNew = false;
    }

    public boolean setTopItemGuard() {
        return state == State.CREATED;
    }
    @Action
    public void setTopItem() {

        FakeItem currentTop = timeline.getTopItem().orElse(null);

        FakeItem nextTop = timeline.getItems().stream().filter(i -> !Objects.equals(currentTop, i))
                .findFirst().orElse(null);
        while (nextTop == null) {
            this.fetchItems();
            nextTop = timeline.getItems().stream().filter(i -> !Objects.equals(currentTop, i))
                    .findFirst().orElse(null);
        }

        timeline.setTopItem(nextTop);

        assertThat(timeline.getTopItem()).contains(nextTop);
        MementoAssert.assertThat(captureStorageMemento(++currentStorageCall))
                .isEqualTo(createMemento(nextTop, timeline.getItems()));
    }

    public boolean setTopItemIfNoElementOfItemListGuard() {
        return state == State.CREATED;
    }
    @Action
    public void setTopItemIfNoElementOfItemList() {
        Throwable actual = thrownBy(() -> timeline.setTopItem(FIRST_ITEM));

        assertThat(actual)
                .hasMessageContaining(FIRST_ITEM.getId())
                .isInstanceOf(IllegalArgumentException.class);
    }

    public boolean setTopItemWithNullGuard() {
        return state == State.CREATED;
    }
    @Action
    public void setTopItemWithNull() {
        Throwable actual = thrownBy(() -> timeline.setTopItem(null));

        assertThat(actual)
                .hasMessageContaining(Messages.get("ERROR_TOP_ITEM_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }


    // Utils
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Memento<FakeItem> captureStorageMemento(int numberOfInvocations) {
        ArgumentCaptor<Memento> captor = forClass(Memento.class);
        verify(sessionStorage, times(numberOfInvocations)).store(captor.capture());
        return captor.getValue();
    }

    @SuppressWarnings("unchecked")
    private SessionStorage<FakeItem> stubSessionStorage() {
        SessionStorage<FakeItem> result = mock(SessionStorage.class);
        when(result.read()).thenReturn(Memento.empty());
        return result;
    }

    private SessionStorage<FakeItem> stubSessionStorage(FakeItem first, FakeItem...items) {
        SessionStorage<FakeItem> result = mock(SessionStorage.class);
        when(result.read()).thenReturn(createMemento(first, items));
        return result;
    }

    private Memento<FakeItem> createMemento(FakeItem topItem, FakeItem... items) {
        return createMemento(topItem, asList(items));
    }
    private Memento<FakeItem> createMemento(FakeItem topItem, List<FakeItem> items) {
        return new Memento<>(new HashSet<>(items), Optional.ofNullable(topItem));
    }

    private FakeItem[] createNewItems(int count) {
        FakeItem[] newItems = FakeItemUtils.createNewItems(maxItemId, count);
        maxItemId += count;
        return newItems;
    }

    private FakeItem[] createMoreItems(int count) {
        FakeItem[] newItems = FakeItemUtils.createMoreItems(minItemId, count);
        minItemId -= count;
        return newItems;
    }


    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this,  "timeline-fms.dot");
    }
}
