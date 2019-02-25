package org.jcy.timeline.swing;

import org.jcy.timeline.core.model.Item;
import org.jcy.timeline.swing.ui.HeaderProxy;
import org.jcy.timeline.swing.ui.ItemViewerProxy;

import static org.jcy.timeline.swing.TimelineStateMachine.MainState.RUNNING;

public class SwingUIUpdateListener<I extends Item, U> {

    private final TimelineSwingFsmTest TEST = TimelineSwingFsmTest.getInstance();

    private final int COMPONENT_COUNT;

    private volatile int counter = 0;

    public SwingUIUpdateListener(int componentCount) {
        COMPONENT_COUNT = componentCount;
    }


    public synchronized void register(HeaderProxy<I> headerProxy) {
        if (counter < COMPONENT_COUNT) {
            headerProxy.addUpdateListener(this);
            counter++;
        }
    }

    public synchronized void register(ItemViewerProxy<I, U> itemViewerProxy) {
        if (counter < COMPONENT_COUNT) {
            itemViewerProxy.addUpdateListener(this);
            counter++;
        }
    }

    public synchronized void updateCompleted() {
        counter--;
        if (counter <= 0) {
            counter += COMPONENT_COUNT;
            TEST.state.setRealUpdating(false);
        }
    }

    public synchronized void updateCompleted(boolean fetchNewVisiable) {
        counter--;
        if (counter <= 0) {
            counter += COMPONENT_COUNT;
            TEST.state.setRealUpdating(false, fetchNewVisiable);
        }
    }

    public synchronized boolean canUpdate() {
        if (TEST.state.is(RUNNING)) {
            TEST.state.setRealUpdating(true);
            return true;
        }
        return false;
    }

}
