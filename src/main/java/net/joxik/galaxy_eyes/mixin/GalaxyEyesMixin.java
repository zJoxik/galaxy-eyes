package net.joxik.galaxy_eyes.mixin;

import net.joxik.galaxy_eyes.GalaxyEyes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.GameRenderer;


@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GalaxyEyesMixin {
    @Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
    public void getZoomLevel(CallbackInfoReturnable<Double> info) {
        if (GalaxyEyes.isZooming()) {
            double fov = info.getReturnValue();
            info.setReturnValue(fov * GalaxyEyes.zoomLevel);
        }
    }
}
