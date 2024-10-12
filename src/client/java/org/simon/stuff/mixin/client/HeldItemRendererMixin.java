package org.simon.stuff.mixin.client;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.joml.Quaternionf;
import org.simon.stuff.item.SmokeableItem;
import org.simon.stuff.item.StuffUseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void injectCustomItemRendering(net.minecraft.client.network.AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, net.minecraft.client.render.VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (item.getItem() instanceof SmokeableItem && player.isUsingItem() && player.getActiveItem() == item) {
            SmokeableItem smokeableItem = (SmokeableItem) item.getItem();
            if (smokeableItem.getCustomUseAction(item) == StuffUseAction.SMOKE_JOINT) {
                // Apply custom transformation for smoking a joint
                matrices.translate(-0.3, 0.3, 0.0); // Raise the item
                matrices.multiply(new Quaternionf().rotateXYZ(
                        (float) Math.toRadians(30),
                        (float) Math.toRadians(-25),
                        (float) Math.toRadians(0)
                ));
            }
        }
    }
}