package org.simon.stuff.entity;

import org.simon.stuff.Stuff;
import org.simon.stuff.item.StuffItems;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Created by lukas on 25.04.14.
 *
 * Updated by Sollace on 1 Jan 2023
 */
public interface StuffEntities {

    EntityType<RealityRiftEntity> REALITY_RIFT = register("reality_rift", FabricEntityTypeBuilder.create(SpawnGroup.MISC, RealityRiftEntity::new)
            .trackedUpdateRate(3).trackRangeBlocks(80)
            .dimensions(EntityDimensions.fixed(2F, 2F)));

    static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> builder) {
        EntityType<T> type = builder.build();
        return Registry.register(Registries.ENTITY_TYPE, Stuff.id(name), type);
    }

    static void bootstrap() {

    }
}
