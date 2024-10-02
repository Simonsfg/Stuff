package org.simon.stuff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.simon.stuff.item.CoinItem;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import org.simon.stuff.entity.EggEntity;

public class Stuff implements ModInitializer {

    public static final String MOD_ID = "stuff";

    public static final EntityType<EggEntity> EGG = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "egg"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EggEntity::new)
                    .dimensions(EntityDimensions.fixed(3.1f, 4.4f))
                    .forceTrackedVelocityUpdates(true)
                    .trackedUpdateRate(10)
                    .trackedUpdateRate(20)
                    .build()
    );

    public static final Item COIN = registerItem("coin", new CoinItem(new FabricItemSettings()));


    @Override
    public void onInitialize() {
        // No need to register the item group separately
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }
}



