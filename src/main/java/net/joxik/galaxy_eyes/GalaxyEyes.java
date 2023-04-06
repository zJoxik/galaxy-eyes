package net.joxik.galaxy_eyes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.option.KeyBinding;

import org.lwjgl.glfw.GLFW;

public class GalaxyEyes implements ClientModInitializer {

    private static KeyBinding keyBind;
    private static double zoomFov = 30;

    @Override
    public void onInitializeClient() {
        keyBind = new KeyBinding("key.galaxy_eyes.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.galaxy_eyes.zoom");
        KeyBindingHelper.registerKeyBinding(keyBind);
    }

    public static boolean isZooming() {
        return keyBind.isPressed();
    }

    public static double getZoomFov() {
        return zoomFov;
    }
}
