package org.jcy.timeline.swing;

public class TimelineStateMachine {

    private volatile State state;

    public enum State {
        START,
        INITIALIZING_TIMELINE_CREATED,
        INITIALIZING_UI_DISPLAYED,
        RUNNING_TIME_WAITING,
        RUNNING_UPDATING,

    }

    public synchronized State getState() {
        return state;
    }

    public synchronized void setState(State state) {
        this.state = state;
    }

    public synchronized boolean setIfMatch(State nextState, State... expects) {
        if (is(expects)) {
            state = nextState;
            return true;
        }
        return false;
    }

    public boolean is(State... expects) {
        if (expects != null) {
            for (State e : expects) {
                if (state == e) {
                    return true;
                }
            }
        }
        return false;
    }

    public String name() {
        return state.name();
    }

    public synchronized void reset(){
        state = State.START;
    }
}
