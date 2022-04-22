package net.morimori0317.bettertaskbar.api;

import net.morimori0317.bettertaskbar.BetterTaskbar;

public class BetterTaskbarAPI {
    private static final BetterTaskbarAPI INSTANCE = new BetterTaskbarAPI();
    private boolean updated;
    private float lastProgress;
    private State lastState;
    private int lastComp;
    private int lastTotal;

    public static BetterTaskbarAPI getInstance() {
        return INSTANCE;
    }

    public void setProgress(int comp, int total) {
        setState(BetterTaskbarAPI.State.NORMAL);
        boolean flg = false;
        if (lastComp != comp) {
            lastComp = comp;
            flg = true;
        }
        if (lastTotal != total) {
            lastTotal = total;
            flg = true;
        }

        if (flg) {
            var ac = BetterTaskbar.getTaskbarAccess();
            if (ac != null)
                ac.setProgress(comp, total);
            updated = true;
        }
    }

    public void setProgress(float value) {
        setState(BetterTaskbarAPI.State.NORMAL);
        if (lastProgress != value) {
            lastProgress = value;

            var ac = BetterTaskbar.getTaskbarAccess();
            if (ac != null)
                ac.setProgress(value);

            updated = true;
        }
    }

    public void setState(State state) {
        if (lastState != state) {
            lastState = state;

            var ac = BetterTaskbar.getTaskbarAccess();
            if (ac != null)
                ac.setState(state);

            updated = true;
        }
    }

    public enum State {
        NO_PROGRESS, WAIT, ERROR, NORMAL, PAUSE;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
