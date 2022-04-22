package net.morimori0317.bettertaskbar.jni.win;

public class WindowsNative {
    protected static native boolean setTaskbarProgressValue(long id, int completed, int total);

    protected static native boolean setTaskbarProgressState(long id, int flags);
}
