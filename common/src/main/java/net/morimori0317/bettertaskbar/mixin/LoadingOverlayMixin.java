package net.morimori0317.bettertaskbar.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.morimori0317.bettertaskbar.api.BetterTaskbarAPI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LoadingOverlay.class)
public class LoadingOverlayMixin {

    @Shadow
    private float currentProgress;

    @Shadow
    @Final
    private ReloadInstance reload;

    @Inject(method = "render", at = @At("HEAD"))
    private void render(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        var api = BetterTaskbarAPI.getInstance();
        if (reload.isDone()) {
            api.setState(BetterTaskbarAPI.State.NO_PROGRESS);
        } else {
            api.setProgress(currentProgress);
        }
    }
}
