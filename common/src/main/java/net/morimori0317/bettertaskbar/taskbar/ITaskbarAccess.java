package net.morimori0317.bettertaskbar.taskbar;

import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;

public interface ITaskbarAccess {
    default void setProgress(float par) {
        setProgress((int) (par * 100), 100);
    }

    void setProgress(int comp, int total);

    void setState(BetterTaskbarAPI.State state);
}
