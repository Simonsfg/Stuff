package org.simon.stuff.item;

import org.joml.Vector3f;

import org.simon.stuff.Stuff;
import org.simon.stuff.block.StuffBlocks;
import org.simon.stuff.entity.drug.*;
import org.simon.stuff.entity.drug.influence.DrugInfluence;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Created by lukas on 25.04.14.
 * Updated by Sollace on 1 Jan 2023
 */
public interface StuffItems {

    Item CANNABIS_SEEDS = register("cannabis_seeds", new AliasedBlockItem(StuffBlocks.CANNABIS, new Settings()));
    Item CANNABIS_LEAF = register("cannabis_leaf");
    Item CANNABIS_BUDS = register("cannabis_buds");
    Item DRIED_CANNABIS_LEAF = register("dried_cannabis_leaf");
    Item DRIED_CANNABIS_BUDS = register("dried_cannabis_buds");

    Item VOMIT = register("vomit", new Item(new Settings()));
    Item PAPER_BAG = register("paper_bag", new PaperBagItem(new Settings()));
    Item BAG_O_VOMIT = register("bag_o_vomit", new SuspiciousItem(new Settings()
            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).meat().alwaysEdible().build()
            ), SuspiciousItem.createForms(Items.COOKIE, Items.MUSHROOM_STEW, Items.GOLDEN_APPLE, Items.COOKED_BEEF, Items.COOKED_CHICKEN)));

    SmokeableItem JOINT = register("joint", new SmokeableItem(
            new Settings().maxCount(1).maxDamage(2), 2, new Vector3f(0.9F, 0.9F, 0.9F),
            new DrugInfluence(DrugType.CANNABIS, DrugInfluence.DelayType.INHALED, 0.002, 0.001, 0.20f)
    ));

    static Item register(String name, Block block) {
        return register(name, new BlockItem(block, new Settings()));
    }

    static Item register(String name) {
        return register(name, new Item(new Settings()));
    }

    static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, Stuff.id(name), item);
    }

    static void bootstrap() {

    }
}
