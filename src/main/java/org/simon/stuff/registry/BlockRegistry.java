package org.simon.stuff.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.block.WeedCropBlock;

public class BlockRegistry {

    public static final Block WEED_CROP = Registry.register(Registries.BLOCK, new Identifier(Stuff.MOD_ID, "weed_crop"),
            new WeedCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Stuff.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Stuff.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Stuff.LOGGER.info("Registering ModBlocks for " + Stuff.MOD_ID);
    }
}