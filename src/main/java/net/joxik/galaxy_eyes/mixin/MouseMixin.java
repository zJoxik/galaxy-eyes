package net.joxik.galaxy_eyes.mixin;

import net.joxik.galaxy_eyes.GalaxyEyes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    public void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (window != MinecraftClient.getInstance().getWindow().getHandle()) {
            return;
        }

        if (GalaxyEyes.onScroll(vertical)) {
            ci.cancel();
        }
    }
}
