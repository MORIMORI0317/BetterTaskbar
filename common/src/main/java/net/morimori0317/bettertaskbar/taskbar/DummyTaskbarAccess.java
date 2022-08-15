package net.morimori0317.bettertaskbar.taskbar;

import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;

public class DummyTaskbarAccess implements ITaskbarAccess {

    @Override
    public void setProgress(int comp, int total) {

    }

    @Override
    public void setState(BetterTaskbarAPI.State state) {

    }

    @Override
    public String getName() {
        return "Dummy";
    }
}
