package net.morimori0317.bettertaskbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.morimori0317.bettertaskbar.jni.win.WindowsLibrary;
import org.lwjgl.glfw.GLFWNativeWin32;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class BetterTaskbarAPI {
    private static final BetterTaskbarAPI INSTANCE = new BetterTaskbarAPI();
    private static final Minecraft mc = Minecraft.getInstance();
    public final List<Function<Screen, ProgressTotal>> progressScreens = new ArrayList<>();
    private boolean support;

    private BetterTaskbarAPI() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        String arc = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        this.support = os.contains("windows") && arc.contains("amd64");
    }

    public static BetterTaskbarAPI getInstance() {
        return INSTANCE;
    }

    public Path getLibraryFolderPath() {
        return Paths.get(".");
    }

    public boolean isSupport() {
        return support;
    }

    public boolean setProgress(int comp, int total) {
        long id = GLFWNativeWin32.glfwGetWin32Window(mc.getWindow().getWindow());
        return WindowsLibrary.setTaskbarProgress(id, Mth.clamp(comp, 0, total), total);
    }

    public boolean setProgress(double par) {
        return setProgress((int) (par * 100), 100);
    }

    public boolean setState(State state) {
        long id = GLFWNativeWin32.glfwGetWin32Window(mc.getWindow().getWindow());
        WindowsLibrary.ProgressState ws = null;

        if (state == null) {
            ws = WindowsLibrary.ProgressState.TBPF_NORMAL;
        } else {
            switch (state) {
                case WAIT -> ws = WindowsLibrary.ProgressState.TBPF_INDETERMINATE;
                case ERROR -> ws = WindowsLibrary.ProgressState.TBPF_ERROR;
                case NO_PROGRESS -> ws = WindowsLibrary.ProgressState.TBPF_NOPROGRESS;
                case NOMAL -> ws = WindowsLibrary.ProgressState.TBPF_NORMAL;
                case PAUSE -> ws = WindowsLibrary.ProgressState.TBPF_PAUSED;
            }
        }

        return ws != null && ws.setState(id);
    }

    public void registerProgressScreenPar(Function<Screen, Double> progress) {
        registerProgressScreen(n -> new ProgressTotal((int) (progress.apply(n) * 100), 100, null));
    }

    public void registerProgressScreen(Function<Screen, ProgressTotal> progress) {
        progressScreens.add(progress);
    }

    public record ProgressTotal(int complete, int total, State state) {
    }

    public enum State {
        NO_PROGRESS,
        WAIT,
        ERROR,
        NOMAL,
        PAUSE;
    }
}