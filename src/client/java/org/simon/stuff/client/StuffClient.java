package org.simon.stuff.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.simon.stuff.Stuff;
import org.simon.stuff.client.render.EggEntityRenderer;
import software.bernie.geckolib.GeckoLib;

public class StuffClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        EntityRendererRegistry.register(Stuff.EGG, EggEntityRenderer::new);
    }
}
