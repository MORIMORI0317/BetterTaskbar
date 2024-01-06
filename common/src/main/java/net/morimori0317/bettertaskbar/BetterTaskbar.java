package net.morimori0317.bettertaskbar;

import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;
import net.morimori0317.bettertaskbar.taskbar.DummyTaskbarAccess;
import net.morimori0317.bettertaskbar.taskbar.ITaskbarAccess;
import net.morimori0317.bettertaskbar.taskbar.windows.WindowsTaskbarJNAAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class BetterTaskbar {
    public static final String MODID = "bettertaskbar";
    private static final Logger LOGGER = LogManager.getLogger(BetterTaskbar.class);
    private static ITaskbarAccess taskbarAccess;

    @NotNull
    public static ITaskbarAccess getTaskbarAccess() {
        if (taskbarAccess == null) {
            taskbarAccess = createTaskbarAccess();
            if (taskbarAccess instanceof DummyTaskbarAccess) {
                LOGGER.warn("Better Taskbar unsupported OS or architecture");
            } else {
                LOGGER.info("Better Taskbar is available (" + taskbarAccess.getName() + ")");
            }
        }
        return taskbarAccess;
    }

    private static ITaskbarAccess createTaskbarAccess() {
        String os = System.getProperty("os.name").toLowerCase();
        String arc = System.getProperty("os.arch").toLowerCase();

        if (os.contains("windows") && arc.contains("amd64")) {

            if (WindowsTaskbarJNAAccess.check() && processCheck(WindowsTaskbarJNAAccess.getInstance())) {
                return WindowsTaskbarJNAAccess.getInstance();
            }
        }

        return new DummyTaskbarAccess();
    }

    private static boolean processCheck(ITaskbarAccess taskbarAccess) {
        try {
            taskbarAccess.setState(BetterTaskbarAPI.State.WAIT);
            taskbarAccess.setState(BetterTaskbarAPI.State.ERROR);
            taskbarAccess.setState(BetterTaskbarAPI.State.NORMAL);
            taskbarAccess.setState(BetterTaskbarAPI.State.PAUSE);
            taskbarAccess.setProgress(1, 3);
            taskbarAccess.setProgress(2, 3);
            taskbarAccess.setProgress(0.114514f);

            taskbarAccess.setProgress(0f);
            taskbarAccess.setState(BetterTaskbarAPI.State.NO_PROGRESS);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
