package net.morimori0317.bettertaskbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.*;
import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;

public class TaskbarHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static Screen lastScreen;

    public static void tick() {
        if (mc.screen != null) screenTick();
        if (lastScreen != mc.screen) {
            if (lastScreen != null) screenChange(lastScreen);
            lastScreen = mc.screen;
        }
    }

    private static void screenTick() {
        var api = BetterTaskbarAPI.getInstance();
       
        if (mc.screen instanceof LevelLoadingScreen loadingScreen) {
            api.setProgress(Math.max(loadingScreen.progressListener.getProgress(), 1), 100);
        } else if (mc.screen instanceof ProgressScreen || mc.screen instanceof ConnectScreen || mc.screen instanceof ReceivingLevelScreen) {
            api.setState(BetterTaskbarAPI.State.WAIT);
        } else if (mc.screen instanceof ConfirmScreen) {
            api.setState(BetterTaskbarAPI.State.PAUSE);
        } else if (mc.screen instanceof DisconnectedScreen) {
            api.setState(BetterTaskbarAPI.State.ERROR);
        }
    }

    private static void screenChange(Screen lastScreen) {
        var api = BetterTaskbarAPI.getInstance();
        if (api.isUpdated() && (lastScreen instanceof DisconnectedScreen || lastScreen instanceof LevelLoadingScreen || lastScreen instanceof ProgressScreen || lastScreen instanceof ConnectScreen || lastScreen instanceof ConfirmScreen || lastScreen instanceof ReceivingLevelScreen)) {
            api.setState(BetterTaskbarAPI.State.NO_PROGRESS);
            api.setProgress(0);
            api.setUpdated(false);
        }
    }
}
