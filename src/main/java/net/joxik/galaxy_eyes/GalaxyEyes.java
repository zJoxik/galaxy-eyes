package net.joxik.galaxy_eyes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.option.KeyBinding;

import org.lwjgl.glfw.GLFW;

public class GalaxyEyes implements ClientModInitializer {

    private static final double MIN_ZOOM_FOV = 1.0;
    private static final double MAX_ZOOM_FOV = 170.0;
    private static final MinecraftClient MC = MinecraftClient.getInstance();

    private static KeyBinding keyBind;
    private static double mouseSensitivity = -1.0;
    private static double zoomSensitivity = 1.0;
    private static double zoomFov = 30;
    private static boolean isZoomActive;

    @Override
    public void onInitializeClient() {
        keyBind = new KeyBinding("key.galaxy_eyes.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.galaxy_eyes.zoom");
        KeyBindingHelper.registerKeyBinding(keyBind);
    }

    public static void onZoomActivated() {
        setZoomMouseSens();

        isZoomActive = true;
    }

    public static void onZoomDeactivated() {
        if (!isZoomActive) return;
        resetZoomMouseSens();

        isZoomActive = false;
    }

    public static boolean onScroll(double vertical) {
        if (isZooming() && MC.player != null) {
            if (MC.player.input.sneaking) {
                zoomSensitivity = (zoomFov == 1.0) ? 4.0 : 5.0;
            }
            zoomFov = Math.max(MIN_ZOOM_FOV, Math.min(MAX_ZOOM_FOV, zoomFov + (-vertical * zoomSensitivity)));
            MC.player.sendMessage(Text.translatable("message.galaxy_eyes.zoom", zoomFov), true);

            zoomSensitivity = 1.0;
            return true;
        }

        return false;
    }

    public static boolean isZooming() {
        return keyBind.isPressed();
    }

    public static double getZoomFov() {
        return zoomFov;
    }

    private static void setZoomMouseSens() {
        if (mouseSensitivity <= 0.0 || mouseSensitivity > 1.0) {
            mouseSensitivity = MC.options.getMouseSensitivity().getValue();
        }
        double sens = mouseSensitivity * zoomFov * 2.3 / 100.0;
        MC.options.getMouseSensitivity().setValue(Math.min(sens, mouseSensitivity));
    }

    private static void resetZoomMouseSens() {
        if (mouseSensitivity > 0.0) {
            MC.options.getMouseSensitivity().setValue(mouseSensitivity);
            mouseSensitivity = -1.0;
        }
    }
}
