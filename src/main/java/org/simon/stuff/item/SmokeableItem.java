package org.simon.stuff.item;

import net.minecraft.entity.ItemEntity;

import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.drug.DrugProperties;
import org.simon.stuff.entity.drug.DrugType;
import org.simon.stuff.network.JointEffectPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class SmokeableItem extends Item {

    private final int effectDuration = 20 * 20;

    public SmokeableItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE; // Use the vanilla CUSTOM action
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20; // 1 second of "smoking" animation
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity player) {
            DrugProperties drugProperties = DrugProperties.of(player);
            drugProperties.addToDrug(DrugType.CANNABIS, 1.0);
            drugProperties.startTrip(effectDuration);
            
            // Send packet to client to start the visual effect
            JointEffectPacket.send(player, effectDuration);
            
            if (!player.getAbilities().creativeMode) {
                // Check if the item will break with this use
                if (stack.getDamage() + 1 >= stack.getMaxDamage()) {
                    // Play custom break sound
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    
                    // Decrease the stack without calling damage()
                    stack.decrement(1);
                } else {
                    // If not breaking, just damage the item normally
                    stack.damage(1, player, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                }
            }
        }
        return stack;
    }

    public StuffUseAction getCustomUseAction(ItemStack stack) {
        return StuffUseAction.SMOKE_JOINT;
    }

}