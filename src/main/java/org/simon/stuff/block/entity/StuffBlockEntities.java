package org.simon.stuff.block.entity;

import org.simon.stuff.Stuff;
import org.simon.stuff.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.Builder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface StuffBlockEntities {
    BlockEntityType<PlacedDrinksBlock.Data> PLACED_DRINK = create("placed_drink", BlockEntityType.Builder.create(PlacedDrinksBlock.Data::new, StuffBlocks.PLACED_DRINK));


    static <T extends BlockEntity> BlockEntityType<T> create(String id, Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Stuff.id(id), builder.build(null));
    }

    static void bootstrap() { }
}

