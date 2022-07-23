package online.cszt0.magicworld;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import online.cszt0.magicworld.entities.WanderMagicianRender;

@Environment(EnvType.CLIENT)
public class MagicWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MagicWorld.WANDER_MAGICIAN, WanderMagicianRender::new);
    }
}
