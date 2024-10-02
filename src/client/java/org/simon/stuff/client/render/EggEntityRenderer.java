package org.simon.stuff.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.EggEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.RotationAxis;

public class EggEntityRenderer extends GeoEntityRenderer<EggEntity> {
    public EggEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EggEntityModel());
        this.scaleWidth = 1.0f;
        this.scaleHeight = 1.0f;
    }

    @Override
    public void render(EggEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.push();
        poseStack.scale(5.0f, 5.0f, 5.0f);

        // Add wiggle animation
        float wiggleAmount = 2.0f; // Max rotation in degrees
        float wiggleSpeed = 1.0f; // Speed of the wiggle
        float wiggleAngle = (float) Math.sin(entity.age * wiggleSpeed * 0.05f) * wiggleAmount;
        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(wiggleAngle));

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.pop();
    }
}

class EggEntityModel extends GeoModel<EggEntity> {
    @Override
    public Identifier getModelResource(EggEntity object) {
        return new Identifier(Stuff.MOD_ID, "geo/entity/egg.geo.json");
    }

    @Override
    public Identifier getTextureResource(EggEntity object) {
        return new Identifier(Stuff.MOD_ID, "textures/entity/egg.png");
    }

    @Override
    public Identifier getAnimationResource(EggEntity animatable) {
        return null; // No animations
    }
}