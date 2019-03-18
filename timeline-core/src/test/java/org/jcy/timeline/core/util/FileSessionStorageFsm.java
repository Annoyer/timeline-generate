package org.jcy.timeline.core.util;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.core.CoreFsmTestRunner;
import org.jcy.timeline.core.model.FakeItem;
import org.jcy.timeline.core.model.FakeItemSerialization;
import org.jcy.timeline.core.model.Memento;
import org.jcy.timeline.core.model.MementoAssert;
import org.jcy.timeline.util.Messages;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jcy.timeline.core.ThrowableCaptor.thrownBy;
import static org.jcy.timeline.core.model.FakeItemUtils.ALL_ITEMS;
import static org.jcy.timeline.core.model.FakeItemUtils.FIRST_ITEM;

/**
 * Test with different inputs in a same action.
 */
public class FileSessionStorageFsm implements FsmModel {

    private TemporaryFolder temporaryFolder;

    private enum State {START, CREATED, STORED}

    private State state;

    private FileSessionStorage<FakeItem> sessionStorage;
    private File storageLocation;
    private Memento<FakeItem> expected;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
        if (temporaryFolder != null) {
            temporaryFolder.delete();
            temporaryFolder = null;
        }
        sessionStorage = null;
        storageLocation = null;
        expected = null;
    }

    public boolean createGuard() {
        return state == State.START;
    }
    @Action
    public void create() throws IOException {
        temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        this.constructTests();
        storageLocation = temporaryFolder.newFile();
        sessionStorage = this.createStorage(storageLocation);
        state = State.CREATED;
    }
    private void constructTests() throws IOException {
        constructIfStorageLocationIsNoFile();
        constructIfStorageLocationIsNotAccesible();
        constructWithNullAsItemSerialisation();
        constructWithNullAsStorageLocation();
    }
    private void constructIfStorageLocationIsNotAccesible() {
        File nonExistingLocation = new File("doesNotExist");

        Throwable actual = thrownBy(() -> new FileSessionStorage<>(nonExistingLocation, new FakeItemSerialization()));

        assertThat(actual)
                .hasMessageContaining(nonExistingLocation.toString())
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void constructIfStorageLocationIsNoFile() throws IOException {
        File folder = temporaryFolder.newFolder();

        Throwable actual = thrownBy(() -> new FileSessionStorage<>(folder, new FakeItemSerialization()));

        assertThat(actual)
                .hasMessageContaining(folder.toString())
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void constructWithNullAsStorageLocation() {
        Throwable actual = thrownBy(() -> new FileSessionStorage<>(null, new FakeItemSerialization()));

        assertThat(actual)
                .hasMessageContaining(Messages.get("STORAGE_LOCATION_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }


    private void constructWithNullAsItemSerialisation() {
        Throwable actual = thrownBy(() -> new FileSessionStorage<>(temporaryFolder.newFile(), null));

        assertThat(actual)
                .hasMessageContaining(Messages.get("ITEM_SERIALIZATION_MUST_NOT_BE_NULL"))
                .isInstanceOf(IllegalArgumentException.class);
    }


    public boolean storeGuard() {
        return state == State.CREATED || state == State.STORED;
    }
    @Action
    public void store() throws IOException {
        this.storeTests();
        FileSessionStorage<FakeItem> storage = createStorage(storageLocation);
        expected = createMemento();

        storage.store(expected);
        Memento<FakeItem> actual = storage.read();

        assertThat(storedMemento(storageLocation)).isNotEmpty();
        MementoAssert.assertThat(actual)
                .isEqualTo(expected)
                .isNotSameAs(expected);
        state = State.STORED;
    }
    private void storeTests() throws IOException {
        storageOfEmptyMemento();
        storeWithIOProblem();
    }
    private void storageOfEmptyMemento() throws IOException {
        File storageLocation = temporaryFolder.newFile();
        FileSessionStorage<FakeItem> storage = createStorage(storageLocation);
        storeNonEmptyMemento(storage);

        storage.store(Memento.empty());
        Memento<FakeItem> actual = storage.read();

        assertThat(storedMemento(storageLocation)).isEmpty();
        MementoAssert.assertThat(actual)
                .isEqualTo(Memento.empty())
                .isSameAs(Memento.empty());
    }
    private static void storeNonEmptyMemento(FileSessionStorage<FakeItem> storage) {
        storage.store(createMemento());
    }

    public void storeWithIOProblem() throws IOException {
        File storageLocation = temporaryFolder.newFile();
        FileSessionStorage<FakeItem> storage = createStorage(storageLocation);
        Memento<FakeItem> expected = createMemento();
        storageLocation.delete();

        Throwable actual = thrownBy(() -> storage.store(expected));

        assertThat(actual)
                .isInstanceOf(IllegalStateException.class)
                .hasCauseInstanceOf(IOException.class);
    }
    private FileSessionStorage<FakeItem> createStorage(File storageLocation) {
        return new FileSessionStorage<>(storageLocation, new FakeItemSerialization());
    }

    public boolean readGuard() {
        return state == State.STORED && expected != null;
    }
    @Action
    public void read() throws IOException {
        this.readWithIOProblem();

        Memento<FakeItem> actual = sessionStorage.read();

        assertThat(storedMemento(storageLocation)).isNotEmpty();
        MementoAssert.assertThat(actual)
                .isEqualTo(expected)
                .isNotSameAs(expected);
    }
    private void readWithIOProblem() throws IOException {
        File storageLocation = temporaryFolder.newFile();
        FileSessionStorage<FakeItem> storage = createStorage(storageLocation);
        storageLocation.delete();

        Throwable actual = thrownBy(() -> storage.read());

        assertThat(actual)
                .isInstanceOf(IllegalStateException.class)
                .hasCauseInstanceOf(IOException.class);
    }


    private static Memento<FakeItem> createMemento() {
        return new Memento<>(ALL_ITEMS, Optional.of(FIRST_ITEM));
    }

    private byte[] storedMemento(File storageLocation) throws IOException {
        return readAllBytes(storageLocation.toPath());
    }

    @Test
    public void runTest() {
        CoreFsmTestRunner.runTest(this, "file-session-storage-fsm.dot");
    }
}
