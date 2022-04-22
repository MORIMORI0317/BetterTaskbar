package net.morimori0317.bettertaskbar.fabric.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.morimori0317.bettertaskbar.TaskbarHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void render(float f, long l, boolean bl, CallbackInfo ci) {
        TaskbarHandler.tick();
    }
}
