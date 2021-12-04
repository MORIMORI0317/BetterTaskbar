package net.morimori0317.bettertaskbar;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;

public class BetterTaskbar implements ClientModInitializer {
    public static final String MODID = "bettertaskbar";

    @Override
    public void onInitializeClient() {
        BetterTaskbarAPI.getInstance().registerProgressScreen(n -> {
            if (n instanceof LevelLoadingScreen loadingScreen)
                return new BetterTaskbarAPI.ProgressTotal(Math.max(loadingScreen.progressListener.getProgress(), 1), 100, null);

            if (n instanceof ProgressScreen || n instanceof ConnectScreen)
                return new BetterTaskbarAPI.ProgressTotal(-1, -1, BetterTaskbarAPI.State.WAIT);

            if (n instanceof ConfirmScreen)
                return new BetterTaskbarAPI.ProgressTotal(-1, -1, BetterTaskbarAPI.State.PAUSE);

            return null;
        });
    }
}
