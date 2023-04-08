package net.joxik.galaxy_eyes.mixin;

import net.joxik.galaxy_eyes.GalaxyEyes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.GameRenderer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void getFov(CallbackInfoReturnable<Double> cir) {
        if (GalaxyEyes.isZooming()) {
            cir.setReturnValue(GalaxyEyes.getZoomFov());
            GalaxyEyes.onZoomActivated();
        }

        if (!GalaxyEyes.isZooming()) {
            GalaxyEyes.onZoomDeactivated();
        }
    }
}
