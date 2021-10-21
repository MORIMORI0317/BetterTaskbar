package net.morimori0317.bettertaskbar.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.morimori0317.bettertaskbar.BetterTaskbarAPI;
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
    private void render(PoseStack poseStack, int l1, int f2, float i2, CallbackInfo ci) {
        BetterTaskbarAPI btm = BetterTaskbarAPI.getInstance();
        if (!btm.isSupport())
            return;

        if (reload.isDone()) {
            btm.setState(BetterTaskbarAPI.State.NO_PROGRESS);
        } else {
            btm.setProgress(currentProgress);
        }
    }
}
