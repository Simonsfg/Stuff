package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.block.CannabisPlantBlock;
import org.simon.stuff.block.StuffBlocks;
import org.simon.stuff.item.StuffItems;
import org.simon.stuff.item.discs.MusicDiscManager;
import org.simon.stuff.registry.ItemRegistry;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerCrop(StuffBlocks.CANNABIS, CannabisPlantBlock.AGE, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(StuffItems.CANNABIS_BUDS, Models.GENERATED);
        itemModelGenerator.register(StuffItems.CANNABIS_LEAF, Models.GENERATED);
        itemModelGenerator.register(StuffItems.DRIED_CANNABIS_BUDS, Models.GENERATED);
        itemModelGenerator.register(StuffItems.DRIED_CANNABIS_LEAF, Models.GENERATED);

        itemModelGenerator.register(StuffItems.BAG_O_VOMIT, Models.GENERATED);
        itemModelGenerator.register(StuffItems.VOMIT, Models.GENERATED);

        MusicDiscManager.MUSIC_DISC_ITEMS.forEach(disc -> itemModelGenerator.register(disc, Models.GENERATED));

    }
}