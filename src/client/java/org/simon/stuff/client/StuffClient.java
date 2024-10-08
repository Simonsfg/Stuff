package org.simon.stuff.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import org.simon.stuff.client.render.EggEntityRenderer;
import org.simon.stuff.registry.BlockRegistry;
import org.simon.stuff.registry.EntityRegistry;
import software.bernie.geckolib.GeckoLib;

public class StuffClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        EntityRendererRegistry.register(EntityRegistry.EGG, EggEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockRegistry.WEED_CROP);
    }
}
