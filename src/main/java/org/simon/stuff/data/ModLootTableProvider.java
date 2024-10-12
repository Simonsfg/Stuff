package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.predicate.StatePredicate;
import org.simon.stuff.block.CannabisPlantBlock;
import org.simon.stuff.block.StuffBlocks;
import org.simon.stuff.item.StuffItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        BlockStatePropertyLootCondition.Builder builder = BlockStatePropertyLootCondition.builder(StuffBlocks.CANNABIS)
            .properties(StatePredicate.Builder.create()
                .exactMatch(CannabisPlantBlock.AGE, CannabisPlantBlock.MAX_AGE));

        addDrop(StuffBlocks.CANNABIS, cropDrops(StuffBlocks.CANNABIS, StuffItems.CANNABIS_LEAF, StuffItems.CANNABIS_SEEDS, builder));

    }
}