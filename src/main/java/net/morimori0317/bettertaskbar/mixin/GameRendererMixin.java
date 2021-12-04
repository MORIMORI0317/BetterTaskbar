package net.morimori0317.bettertaskbar.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.morimori0317.bettertaskbar.BetterTaskbarAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    private static final Minecraft mc = Minecraft.getInstance();
    private Screen lastScreen;

    @Inject(method = "render", at = @At("TAIL"))
    private void render(float f, long l, boolean bl, CallbackInfo ci) {
        onScreenTick();
        if (lastScreen != mc.screen) {
            onScreenChange(lastScreen);
            lastScreen = mc.screen;
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