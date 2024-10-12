package org.simon.stuff.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.*;

public class ItemRegistry {
    public static final Item COIN = registerItem("coin", new CoinItem(new FabricItemSettings()));

    public static final Item EGG_SPAWN_EGG = registerItem("egg_spawn_egg", new EggSpawnEggItem(new FabricItemSettings()));


    public static void addItemToModCreativeTabTools(FabricItemGroupEntries entries) {
        entries.add(COIN);
    }

    public static void addItemToModCreativeTabSpawnEggs(FabricItemGroupEntries entries) {
        entries.add(EGG_SPAWN_EGG);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Stuff.MOD_ID, name), item);
    }

    public static void registerItems() {
        Stuff.LOGGER.info("Registering items for " + Stuff.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ItemRegistry::addItemToModCreativeTabTools);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ItemRegistry::addItemToModCreativeTabSpawnEggs);

    }
}
