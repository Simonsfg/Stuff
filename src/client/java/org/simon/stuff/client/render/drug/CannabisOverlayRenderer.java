package org.simon.stuff.client.render.drug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.client.effect.JointEffectManager;

public class CannabisOverlayRenderer {
    private static final Identifier OVERLAY_TEXTURE = new Identifier(Stuff.MOD_ID, "textures/gui/cannabis_overlay.png");

    public static void render() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !JointEffectManager.isEffectActive()) {
            return;
        }

        float alpha = JointEffectManager.getEffectIntensity();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, OVERLAY_TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D, height, -90.0D).texture(0.0F, 1.0F).next();
        bufferBuilder.vertex(width, height, -90.0D).texture(1.0F, 1.0F).next();
        bufferBuilder.vertex(width, 0.0D, -90.0D).texture(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).texture(0.0F, 0.0F).next();
        tessellator.draw();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}