package org.jcy.timeline.web.ui;

import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.core.provider.git.GitItem;
import org.jcy.timeline.core.ui.AutoUpdate;
import org.jcy.timeline.web.model.GitItemUi;
import org.jcy.timeline.web.model.UpdateInfo;
import org.jcy.timeline.web.websocket.MessageUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class WebAutoUpdate implements AutoUpdate {

    private static final int AUTO_UPDATE_INTERVAL = 5000;

    private volatile boolean isScheduled = false;

    private Timer timer;

    private final Timeline<GitItem> timeline;

    private final String sessionId;

    public WebAutoUpdate(String sessionId, Timeline<GitItem> timeline) {
        this.sessionId = sessionId;
        this.timeline = timeline;
    }

    @Override
    public void start() {
        if (!this.isScheduled) {
            synchronized (this) {
                if (!this.isScheduled) {
                    isScheduled = true;
                    timer = new Timer();
                    timer.schedule(this.createAutoUpdateTask(), 0, AUTO_UPDATE_INTERVAL);
                }
            }
        }
    }

    @Override
    public void stop() {
        if (this.isScheduled) {
            synchronized (this) {
                if (this.isScheduled) {
                    timer.cancel();
                    timer.purge();
                    this.isScheduled = false;
                }
            }
        }
    }

    private TimerTask createAutoUpdateTask() {
        return new TimerTask() {
            @Override
            public void run() {
                List<GitItemUi> itemUIs = timeline.getItems().stream()
                        .map(GitItemUi::new)
                        .collect(Collectors.toList());
                MessageUtils.send(sessionId, new UpdateInfo(timeline.getNewCount(), itemUIs));
            }
        };
    }

}
