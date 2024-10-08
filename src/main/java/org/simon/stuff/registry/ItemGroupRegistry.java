package org.simon.stuff.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;

public class ItemGroupRegistry {
    public static final ItemGroup COIN_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Stuff.MOD_ID, "coin"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.coin"))
                    .icon(() -> new ItemStack(ItemRegistry.COIN)).entries((displayContext, entries) -> {
                        entries.add(ItemRegistry.COIN);
                        entries.add(ItemRegistry.EGG_SPAWN_EGG);
                        entries.add(ItemRegistry.WEED_SEEDS);
                        entries.add(ItemRegistry.WEED_LEAF);
                        entries.add(ItemRegistry.MUSIC_DISC_AMONG_US);
                        entries.add(ItemRegistry.MUSIC_DISC_BITCHES);
                        entries.add(ItemRegistry.MUSIC_DISC_ARSCHRAP);
                        entries.add(ItemRegistry.MUSIC_DISC_HAMBURG);
                        entries.add(ItemRegistry.MUSIC_DISC_DOTA3);
                        entries.add(ItemRegistry.MUSIC_DISC_ITS_LIT);
                        entries.add(ItemRegistry.MUSIC_DISC_LOCKDOWN);

                    }).build());

    public static void registerItemGroups() {
        Stuff.LOGGER.info("Registering Item Groups for " + Stuff.MOD_ID);
    }
}
