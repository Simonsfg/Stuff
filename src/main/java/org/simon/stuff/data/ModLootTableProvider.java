package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import org.simon.stuff.block.WeedCropBlock;
import org.simon.stuff.registry.BlockRegistry;
import org.simon.stuff.registry.ItemRegistry;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(BlockRegistry.WEED_CROP).properties(StatePredicate.Builder.create()
                .exactMatch(WeedCropBlock.AGE, 3));
        addDrop(BlockRegistry.WEED_CROP, cropDrops(BlockRegistry.WEED_CROP, ItemRegistry.WEED_LEAF, ItemRegistry.WEED_SEEDS, builder));

    }
}