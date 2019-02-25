package org.jcy.timeline.swing.ui;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.core.model.Timeline;
import org.jcy.timeline.swing.SwingUIUpdateListener;
import org.jcy.timeline.swing.TestUtil;

public class HeaderProxy<I extends Item> extends Header<I> {

    private SwingUIUpdateListener listener;

    HeaderProxy(Header<I> actual) {
        super((Timeline<I>) TestUtil.findFieldValue("timeline", actual));
    }

    @Override
    protected void update(int count) {
        if (listener != null && listener.canUpdate()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // swallow
            }
            super.update(count);
            if (listener != null) {
                listener.updateCompleted(count > 0);
            }
        }
    }

    public void addUpdateListener(SwingUIUpdateListener listener) {
        this.listener = listener;
    }

}
