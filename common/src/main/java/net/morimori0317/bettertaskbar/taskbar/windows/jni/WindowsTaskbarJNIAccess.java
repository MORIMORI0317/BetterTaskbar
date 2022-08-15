package net.morimori0317.bettertaskbar.taskbar.windows.jni;

import net.minecraft.client.Minecraft;
import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;
import net.morimori0317.bettertaskbar.jni.win.WindowsLibrary;
import net.morimori0317.bettertaskbar.taskbar.ITaskbarAccess;
import org.lwjgl.glfw.GLFWNativeWin32;

public class WindowsTaskbarJNIAccess implements ITaskbarAccess {
    private static final WindowsTaskbarJNIAccess INSTANCE = new WindowsTaskbarJNIAccess();

    public static WindowsTaskbarJNIAccess getInstance() {
        return INSTANCE;
    }

    @Override
    public void setProgress(int comp, int total) {
        var window = Minecraft.getInstance().getWindow();
        if (window == null) return;
        long id = GLFWNativeWin32.glfwGetWin32Window(window.getWindow());
        WindowsLibrary.setTaskbarProgress(id, comp, total);
    }

    @Override
    public void setState(BetterTaskbarAPI.State state) {
        var window = Minecraft.getInstance().getWindow();
        if (window == null) return;
        long id = GLFWNativeWin32.glfwGetWin32Window(window.getWindow());
        WindowsLibrary.setTaskbarProgressState(id, WindowsLibrary.ProgressState.getByState(state));
    }

    @Override
    public String getName() {
        return "Windows JNI";
    }

    public static boolean check() {
        return WindowsLibrary.check();
    }
}
