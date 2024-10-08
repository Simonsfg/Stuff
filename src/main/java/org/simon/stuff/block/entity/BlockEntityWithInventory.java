/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package org.simon.stuff.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.*;

public abstract class BlockEntityWithInventory extends SyncedBlockEntity implements SidedInventory {
    protected static final int[] NO_SLOTS = new int[0];

    private final DefaultedList<ItemStack> inventory;

    public BlockEntityWithInventory(BlockEntityType<? extends BlockEntityWithInventory> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
    }

    @Override
    public void writeNbt(NbtCompound compound) {
        super.writeNbt(compound);
        Inventories.writeNbt(compound, inventory);
    }

    @Override
    public void readNbt(NbtCompound compound) {
        super.readNbt(compound);
        inventory.clear();
        Inventories.readNbt(compound, inventory);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public void clear() {
        inventory.clear();
        onInventoryChanged();
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack removed = Inventories.removeStack(inventory, slot);
        if (!removed.isEmpty()) {
            onInventoryChanged();
        }
        return removed;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack removed = Inventories.splitStack(inventory, slot, amount);
        if (!removed.isEmpty()) {
            onInventoryChanged();
        }
        return removed;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        onInventoryChanged();
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    public void onInventoryChanged() {
        markDirty();
        if (world != null) {
            world.updateNeighbors(pos, getCachedState().getBlock());
            if (world instanceof ServerWorld sw) {
                sw.getChunkManager().markForUpdate(getPos());
            }
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return (getCachedState().equals(player.getWorld().getBlockState(pos)))
                && player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) <= 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }
}
