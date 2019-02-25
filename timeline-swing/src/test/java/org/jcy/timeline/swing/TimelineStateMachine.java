package org.jcy.timeline.swing;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jcy.timeline.swing.TimelineSwingFsmTest.BASE_DIRECTORY;
import static org.jcy.timeline.swing.TimelineSwingFsmTest.REPOSITORY_NAME;

public class TimelineStateMachine {

    private static final Logger log = LoggerFactory.getLogger(TimelineStateMachine.class);

    private volatile MainState state = MainState.START;

    private volatile boolean isRealUpdating = false;

    private volatile Boolean nextBtnNew = null;

    private volatile boolean isUpdating = false;

    private volatile boolean btnMoreVisible = false;

    private volatile boolean btnNewVisible = false;

    private volatile boolean topItemChanged = false;

    private static final String UPDATING = "_UPDATING";

    private static final String TIME_WAITING = "_TIMEWAITING";

    private static final String BTN_MORE_VISIBLE = "_BTNMORE";

    private static final String BTN_NEW_VISIBLE = "_BTNNEW";

    private static final String TOP_ITEM_CHANGED = "_TOPCHANGED";

    private static final ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    public enum MainState {
        START,
        INITIALIZING_CREATED,
        INITIALIZING_DISPLAYED,
        RUNNING

    }

    public synchronized void reset() {
        state = MainState.START;
        isUpdating = false;
        btnNewVisible = false;
        btnMoreVisible = false;
        topItemChanged = false;
        isRealUpdating = false;
        nextBtnNew = null;
    }

    public synchronized String getState() {
        StringBuilder builder = new StringBuilder().append(state.name());
        if (state == MainState.RUNNING) {
           if (isUpdating) {
               builder.append(UPDATING);
           } else {
               builder.append(TIME_WAITING);
           }

           if (btnMoreVisible) {
               builder.append(BTN_MORE_VISIBLE);
           }

           if (btnNewVisible) {
               builder.append(BTN_NEW_VISIBLE);
           }
        }
        if ((state == MainState.RUNNING || state == MainState.INITIALIZING_DISPLAYED)
            && isTopItemChanged()) {
            builder.append(TOP_ITEM_CHANGED);
        }
        return builder.toString();
    }

    public boolean isRealUpdating() {
        return isRealUpdating;
    }

    public void setRealUpdating(boolean realUpdating) {
        this.isRealUpdating = realUpdating;
    }

    public void setRealUpdating(boolean realUpdating, boolean nextBtnNew) {
        this.isRealUpdating = realUpdating;
        this.nextBtnNew = nextBtnNew;
    }

    public synchronized boolean is(MainState state) {
        return this.state == state;
    }

    public synchronized void set(MainState state, boolean isUpdating, boolean btnMoreVisible, boolean btnNewVisible) {
        this.state = state;
        this.isUpdating = isUpdating;
        this.btnMoreVisible = btnMoreVisible;
        this.btnNewVisible = btnNewVisible;
    }

    public synchronized boolean isUpdating() {
        return isUpdating;
    }

    public synchronized void setUpdating() {
        isUpdating = isRealUpdating;
        btnMoreVisible = !isUpdating;
        if (nextBtnNew != null) {
            btnNewVisible = btnNewVisible || nextBtnNew;
            nextBtnNew = null;
        }
    }

    public synchronized boolean isBtnMoreVisible() {
        return btnMoreVisible;
    }

    public synchronized void setBtnMoreVisible(boolean btnMoreVisible) {
        this.btnMoreVisible = btnMoreVisible;
    }

    public synchronized boolean isBtnNewVisible() {
        if (System.currentTimeMillis() % 3 == 1) {
            THREAD_POOL.execute(() -> {
                try {
                    this.mockNewCommit();
                } catch (IOException | GitAPIException e) {
                    log.error("Fail to mock new commit!", e);
                }
            });

        }
        return btnNewVisible;
    }

    private void mockNewCommit() throws IOException, GitAPIException {
        String time = String.valueOf(System.currentTimeMillis());
        File newfile = new File(BASE_DIRECTORY, "/.timeline/" + REPOSITORY_NAME + "/newfile" + time + ".txt");
        newfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(BASE_DIRECTORY.getCanonicalFile() + "/.timeline/" + REPOSITORY_NAME + "/.git"));

        //添加文件
        git.add().addFilepattern("newFile").call();

        git.commit().setMessage("commit at " + time).call();

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("Annoyer","jun08301230");
        git.push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        log.info("[Commit a new comment!]");
    }

    public synchronized void setBtnNewVisible(boolean btnNewVisible) {
        this.btnNewVisible = btnNewVisible;
    }

    public synchronized boolean isTopItemChanged() {
        return topItemChanged;
    }

    public synchronized void setTopItemChanged(boolean topItemChanged) {
        this.topItemChanged = topItemChanged;
    }

}
