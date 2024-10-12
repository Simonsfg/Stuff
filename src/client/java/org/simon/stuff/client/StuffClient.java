package org.simon.stuff.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.RenderLayer;
import org.simon.stuff.block.StuffBlocks;
import org.simon.stuff.client.render.entity.EggEntityRenderer;
import org.simon.stuff.registry.EntityRegistry;
import org.simon.stuff.client.effect.JointEffectManager;
import org.simon.stuff.network.JointEffectPacket;

import software.bernie.geckolib.GeckoLib;

public class StuffClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        EntityRendererRegistry.register(EntityRegistry.EGG, EggEntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), StuffBlocks.CANNABIS);
        ClientPlayNetworking.registerGlobalReceiver(JointEffectPacket.ID, (client, handler, buf, responseSender) -> {
            int duration = buf.readInt();
            client.execute(() -> JointEffectManager.startEffect(duration));
        });
    }
}
