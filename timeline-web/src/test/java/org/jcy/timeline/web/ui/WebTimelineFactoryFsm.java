package org.jcy.timeline.web.ui;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.jcy.timeline.web.WebFsmTestRunner;
import org.jcy.timeline.web.controller.TimelineControllerFsm;
import org.junit.Assert;
import org.junit.Test;

public class WebTimelineFactoryFsm implements FsmModel {

    private static final String URI = "https://github.com/Annoyer/jenkins-web-test.git";
    private static final String PROJECT_NAME = "jenkins-web-test";
    private static final String SESSION_ID = "111";

    private enum State {START, INITIALIZED}

    private State state;

    private WebTimelineFactory webTimelineFactory;

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean testing) {
        state = State.START;
    }


    public boolean initializeGuard() { return state == State.START; }
    @Action
    public void initialize() {
        webTimelineFactory = new WebTimelineFactory();
        WebTimeline timeline = webTimelineFactory.create(SESSION_ID, URI, PROJECT_NAME);
        Assert.assertNotNull(timeline);
        state = State.INITIALIZED;
    }

    @Test
    public void runTests() {
        WebFsmTestRunner.runTest(this, "web-timeline-factory-fsm.dot");
    }
}
