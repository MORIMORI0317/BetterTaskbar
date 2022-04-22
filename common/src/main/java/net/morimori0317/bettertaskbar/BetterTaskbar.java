package net.morimori0317.bettertaskbar;

import net.morimori0317.bettertaskbar.taskbar.ITaskbarAccess;
import net.morimori0317.bettertaskbar.taskbar.windows.WindowsTaskbarAccess;

import javax.annotation.Nullable;

public class BetterTaskbar {
    public static final String MODID = "bettertaskbar";
    private static ITaskbarAccess taskbarAccess;

    public static void init() {

    }

    @Nullable
    public static ITaskbarAccess getTaskbarAccess() {
        if (taskbarAccess == null)
            taskbarAccess = createTaskbarAccess();
        return taskbarAccess;
    }

    private static ITaskbarAccess createTaskbarAccess() {
        String os = System.getProperty("os.name").toLowerCase();
        String arc = System.getProperty("os.arch").toLowerCase();
        if (os.contains("windows") && arc.contains("amd64"))
            return new WindowsTaskbarAccess();
        return null;
    }
}
