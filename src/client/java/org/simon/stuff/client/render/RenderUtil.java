package org.simon.stuff.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.simon.stuff.util.MathUtils;

import java.util.Random;


public class RenderUtil {
    private static final Vector4f POSITION_VECTOR = new Vector4f();
    private static final Vector3f NORMAL_VECTOR = new Vector3f();
    public static final int SCREEN_Z_OFFSET = -90;
    private static final Random RNG = new Random(0L);

    public static Random random(long seed) {
        RNG.setSeed(seed);
        return RNG;
    }

    public static void setColor(int color, boolean hasAlpha) {
        RenderSystem.setShaderColor(
                MathUtils.r(color),
                MathUtils.g(color),
                MathUtils.b(color),
                hasAlpha ? MathUtils.a(color) : 1
        );
    }

    public static VertexConsumer fastVertex(VertexConsumer buffer, MatrixStack matrices, float x, float y, float z) {
        matrices.peek().getPositionMatrix().transform(POSITION_VECTOR.set(x, y, z, 1));
        return buffer.vertex(POSITION_VECTOR.x, POSITION_VECTOR.y, POSITION_VECTOR.z);
    }

    public static void vertex(VertexConsumer buffer, MatrixStack matrices, float x, float y, float z, float u, float v, int light, int overlay) {
        matrices.peek().getPositionMatrix().transform(POSITION_VECTOR.set(x, y, z, 1));
        matrices.peek().getNormalMatrix().transform(NORMAL_VECTOR.set(0, 1, 0));
        buffer.vertex(POSITION_VECTOR.x, POSITION_VECTOR.y, POSITION_VECTOR.z, 1, 1, 1, 1, u, v, light, overlay, NORMAL_VECTOR.x, NORMAL_VECTOR.y, NORMAL_VECTOR.z);
    }

    public static void drawQuad(MatrixStack matrices, float x0, float y0, float x1, float y1) {
        drawQuad(matrices, x0, y0, x1, y1, 0);
    }

    public static void drawQuad(MatrixStack matrices, float x0, float y0, float x1, float y1, float z) {
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        fastVertex(buffer, matrices, x0, y1, z).texture(0, 1).next();
        fastVertex(buffer, matrices, x1, y1, z).texture(1, 1).next();
        fastVertex(buffer, matrices, x1, y0, z).texture(1, 0).next();
        fastVertex(buffer, matrices, x0, y0, z).texture(0, 0).next();
        Tessellator.getInstance().draw();
    }

    public static void drawOverlay(MatrixStack matrices, float alpha,
            int width, int height,
            Identifier texture,
            float u0, float v0,
            float u1, float v1, int offset) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1, alpha);
        RenderSystem.setShaderTexture(0, texture);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        fastVertex(buffer, matrices, -offset, height + offset, SCREEN_Z_OFFSET).texture(u0, v1).next();
        fastVertex(buffer, matrices, width + offset, height + offset, SCREEN_Z_OFFSET).texture(u1, v1).next();
        fastVertex(buffer, matrices, width + offset, -offset, SCREEN_Z_OFFSET).texture(u1, v0).next();
        fastVertex(buffer, matrices, -offset, -offset, SCREEN_Z_OFFSET).texture(u0, v0).next();
        Tessellator.getInstance().draw();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawRepeatingSprite(DrawContext context, Sprite sprite, int x, int y, int width, int height, float r, float g, float b, float a) {
        final int tileSize = 16;
        for (int tileX = 0; tileX < width; tileX += tileSize) {
            for (int tileY = 0; tileY < height; tileY += tileSize) {
                context.drawSprite(x + tileX, y + tileY, 0, tileSize, tileSize, sprite, r, g, b, a);
            }
        }
    }
}
