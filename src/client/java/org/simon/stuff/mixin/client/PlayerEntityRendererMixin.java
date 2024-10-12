package org.simon.stuff.mixin.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import org.simon.stuff.client.model.JointArmPose;
import org.simon.stuff.item.SmokeableItem;
import org.simon.stuff.item.StuffUseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void setModelPose(AbstractClientPlayerEntity player, CallbackInfo ci) {
        PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer)(Object)this).getModel();

        ItemStack stack = player.getActiveItem();
        if (stack.getItem() instanceof SmokeableItem) {
            SmokeableItem item = (SmokeableItem) stack.getItem();
            if (item.getCustomUseAction(stack) == StuffUseAction.SMOKE_JOINT) {
                JointArmPose.applyArmPose(model, player);
            }
        }
    }
}