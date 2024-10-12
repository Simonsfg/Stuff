package org.simon.stuff.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.simon.stuff.item.SmokeableItem;
import org.simon.stuff.item.StuffUseAction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {

    @Shadow @Final public ModelPart rightSleeve;

    @Shadow @Final public ModelPart leftSleeve;

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setAngles*", at = @At("TAIL"))
    private void injectCustomArmPose(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.isUsingItem()) {
            ItemStack activeItem = livingEntity.getActiveItem();
            if (activeItem.getItem() instanceof SmokeableItem) {
                SmokeableItem smokeableItem = (SmokeableItem) activeItem.getItem();
                if (smokeableItem.getCustomUseAction(activeItem) == StuffUseAction.SMOKE_JOINT) {
                    // Adjust arm pose for smoking
                    this.rightSleeve.pitch = (float) Math.toRadians(-80);
                    this.rightArm.pitch = (float) Math.toRadians(-80); // Raise arm

                    this.rightSleeve.yaw = (float) Math.toRadians(-30);
                    this.rightArm.yaw = (float) Math.toRadians(-30);   // Angle slightly inward

                    this.leftSleeve.pitch = (float) Math.toRadians(-10);
                    this.leftArm.pitch = (float) Math.toRadians(-10); // Slight downward angle
                }
            }
        }
    }
}