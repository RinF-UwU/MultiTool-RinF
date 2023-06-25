package rinf.multitoolrinf.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinders {
    private static KeyBinding test;
    public static void load() {
        test = KeyBindingHelper.registerKeyBinding(new KeyBinding("xxx",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "xxx"));
    }
    public static void update() {
        while (test.wasPressed()) {
            MinecraftClient.getInstance().setScreen(new InventoryScreen(MinecraftClient.getInstance().player));
        }
    }
}
