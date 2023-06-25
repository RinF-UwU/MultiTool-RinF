package rinf.multitoolrinf.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class MultiTool_RinFClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBinders.load();
        ClientTickEvents.END_CLIENT_TICK.register(this::endTick);
    }

    private void endTick(MinecraftClient client) {
        KeyBinders.update();
        MouseStats.update();
    }
}
