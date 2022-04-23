package net.morimori0317.bettertaskbar;

import net.morimori0317.bettertaskbar.taskbar.DummyTaskbarAccess;
import net.morimori0317.bettertaskbar.taskbar.ITaskbarAccess;
import net.morimori0317.bettertaskbar.taskbar.windows.WindowsTaskbarAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BetterTaskbar {
    public static final String MODID = "bettertaskbar";
    private static final Logger LOGGER = LogManager.getLogger(BetterTaskbar.class);
    private static ITaskbarAccess taskbarAccess;

    @NotNull
    public static ITaskbarAccess getTaskbarAccess() {
        if (taskbarAccess == null) {
            taskbarAccess = createTaskbarAccess();
            if (taskbarAccess instanceof DummyTaskbarAccess) {
                LOGGER.warn("Better taskbar unsupported OS or architecture");
            } else {
                LOGGER.info("Better Taskbar is available");
            }
        }
        return taskbarAccess;
    }

    private static ITaskbarAccess createTaskbarAccess() {
        String os = System.getProperty("os.name").toLowerCase();
        String arc = System.getProperty("os.arch").toLowerCase();

        if (os.contains("windows") && arc.contains("amd64"))
            return new WindowsTaskbarAccess();

        return new DummyTaskbarAccess();
    }
}
