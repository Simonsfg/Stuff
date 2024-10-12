package org.simon.stuff.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.discs.MusicDiscManager;
import org.simon.stuff.registry.ItemRegistry;

public class ItemGroupRegistry {
    public static final ItemGroup COIN_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Stuff.MOD_ID, "coin"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.coin"))
                    .icon(() -> new ItemStack(ItemRegistry.COIN)).entries((displayContext, entries) -> {
                        entries.add(ItemRegistry.COIN);
                        entries.add(ItemRegistry.EGG_SPAWN_EGG);

                        MusicDiscManager.MUSIC_DISC_ITEMS.forEach(entries::add);
                    }).build());

    public static void registerItemGroups() {
        Stuff.LOGGER.info("Registering Item Groups for " + Stuff.MOD_ID);
    }
}
