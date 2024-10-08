package org.simon.stuff.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.EggEntity;

public class EntityRegistry {
    public static final EntityType<EggEntity> EGG = Registry.register(
        Registries.ENTITY_TYPE,
        new Identifier(Stuff.MOD_ID, "egg"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EggEntity::new)
            .dimensions(EntityDimensions.fixed(3.1f, 4.4f))
            .build()
    );

    public static void registerEntities() {
        Stuff.LOGGER.info("Registering entities for " + Stuff.MOD_ID);
        FabricDefaultAttributeRegistry.register(EGG, EggEntity.createEggAttributes());
    }
}