package net.morimori0317.bettertaskbar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Function;

public class ClientHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static Screen lastScreen;

    @SubscribeEvent
    public static void onTick(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            onScreenTick();
            if (lastScreen != mc.screen) {
                onScreenChange(lastScreen);
                lastScreen = mc.screen;
            }
        }
    }

    private static void onScreenTick() {
        if (mc.screen == null) return;
        BetterTaskbarAPI btm = BetterTaskbarAPI.getInstance();
        if (!btm.isSupport()) return;
        BetterTaskbarAPI.ProgressTotal progress;
        for (Function<Screen, BetterTaskbarAPI.ProgressTotal> progressScreen : BetterTaskbarAPI.getInstance().progressScreens) {
            progress = progressScreen.apply(mc.screen);
            if (progress != null) {
                if (progress.state() == null || progress.state() == BetterTaskbarAPI.State.NOMAL)
                    btm.setProgress(progress.complete(), progress.total());
                btm.setState(progress.state());
                break;
            }
        }
    }

    private static void onScreenChange(Screen last) {
        if (last == null) return;
        BetterTaskbarAPI btm = BetterTaskbarAPI.getInstance();
        if (!btm.isSupport()) return;
        BetterTaskbarAPI.ProgressTotal progress;
        for (Function<Screen, BetterTaskbarAPI.ProgressTotal> progressScreen : BetterTaskbarAPI.getInstance().progressScreens) {
            progress = progressScreen.apply(mc.screen);
            if (progress != null)
                return;
        }
        btm.setState(BetterTaskbarAPI.State.NO_PROGRESS);
    }

}