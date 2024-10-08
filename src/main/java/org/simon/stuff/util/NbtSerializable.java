package org.simon.stuff.util;

import net.minecraft.nbt.NbtCompound;

public interface NbtSerializable {

    default NbtCompound toNbt() {
        NbtCompound tagCompound = new NbtCompound();
        toNbt(tagCompound);
        return tagCompound;
    }

    void toNbt(NbtCompound compound);

    void fromNbt(NbtCompound compound);
}