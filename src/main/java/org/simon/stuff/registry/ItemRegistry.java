package org.simon.stuff.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.*;

public class ItemRegistry {
    public static final Item COIN = registerItem("coin", new CoinItem(new FabricItemSettings()));
    public static final Item WEED_LEAF = registerItem("weed_leaf", new WeedLeaf(new FabricItemSettings()));

    public static final Item WEED_SEEDS = registerItem("weed_seeds", new AliasedBlockItem(BlockRegistry.WEED_CROP, new FabricItemSettings()));

    public static final Item EGG_SPAWN_EGG = registerItem("egg_spawn_egg", new EggSpawnEggItem(new FabricItemSettings()));

    public static final Item MUSIC_DISC_AMONG_US = registerItem("music_disc_among_us", new BaseMusicDisc(7, SoundRegistry.AMONG_US_SOUND, new FabricItemSettings(), 101));
    public static final Item MUSIC_DISC_BITCHES = registerItem("music_disc_bitches", new BaseMusicDisc(7, SoundRegistry.BITCHES_SOUND, new FabricItemSettings(), 285));
    public static final Item MUSIC_DISC_HAMBURG = registerItem("music_disc_hamburg", new BaseMusicDisc(7, SoundRegistry.HAMBURG_SOUND, new FabricItemSettings(), 130));
    public static final Item MUSIC_DISC_ITS_LIT = registerItem("music_disc_its_lit", new BaseMusicDisc(7, SoundRegistry.ITS_LIT_SOUND, new FabricItemSettings(), 90));
    public static final Item MUSIC_DISC_LOCKDOWN = registerItem("music_disc_lockdown", new BaseMusicDisc(7, SoundRegistry.LOCKDOWN_SOUND, new FabricItemSettings(), 131));
    public static final Item MUSIC_DISC_DOTA3 = registerItem("music_disc_dota3", new BaseMusicDisc(7, SoundRegistry.DOTA3_SOUND, new FabricItemSettings(), 201));
    public static final Item MUSIC_DISC_ARSCHRAP = registerItem("music_disc_arschrap", new BaseMusicDisc(7, SoundRegistry.ARSCHRAP_SOUND, new FabricItemSettings(), 74));

    public static final SmokeableItem JOINT = registerItem("joint", new SmokeableItem(
            new Item.Settings().maxCount(1).maxDamage(2), 2, new Vector3f(0.9F, 0.9F, 0.9F),
            new DrugInfluence(DrugType.CANNABIS, DrugInfluence.DelayType.INHALED, 0.002, 0.001, 0.20f)
    ));



    public static void addItemToModCreativeTabTools(FabricItemGroupEntries entries) {
        entries.add(MUSIC_DISC_AMONG_US);
        entries.add(MUSIC_DISC_BITCHES);
        entries.add(MUSIC_DISC_HAMBURG);
        entries.add(MUSIC_DISC_ITS_LIT);
        entries.add(MUSIC_DISC_LOCKDOWN);
        entries.add(MUSIC_DISC_DOTA3);
        entries.add(MUSIC_DISC_ARSCHRAP);

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
