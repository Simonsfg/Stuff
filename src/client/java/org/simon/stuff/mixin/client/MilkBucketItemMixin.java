package org.simon.stuff.mixin.client;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;
import org.simon.stuff.client.effect.JointEffectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void onMilkConsume(net.minecraft.item.ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<net.minecraft.item.ItemStack> cir) {
        if (world.isClient) {
            JointEffectManager.clearEffect();
        }
    }
}