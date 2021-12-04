package net.morimori0317.bettertaskbar.jni.win;

import net.morimori0317.bettertaskbar.jni.JNILibraryLoader;

public class WindowsLibrary {

    private static boolean check() {
        if (!JNILibraryLoader.isInited())
            JNILibraryLoader.init();
        return JNILibraryLoader.isLoaded();
    }

    public static boolean setTaskbarProgress(long id, int completed, int total) {
        if (!check())
            return false;
        return WindowsNative.setTaskbarProgressValue(id, completed, total);
    }

    public static boolean setTaskbarProgressState(long id, ProgressState state) {
        if (!check())
            return false;
        return WindowsNative.setTaskbarProgressState(id, state.getNum());
    }

    public enum ProgressState {
        TBPF_NOPROGRESS(0x0),
        TBPF_INDETERMINATE(0x1),
        TBPF_NORMAL(0x2),
        TBPF_ERROR(0x4),
        TBPF_PAUSED(0x8);
        private final int num;

        private ProgressState(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public boolean setState(long id) {
            return setTaskbarProgressState(id, this);
        }
    }
}