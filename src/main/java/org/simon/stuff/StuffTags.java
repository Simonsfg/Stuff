package org.simon.stuff;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;

public interface StuffTags {
    TagKey<Block> BARRELS = of("barrels");
    TagKey<Block> DRYING_TABLES = of("drying_tables");

    static TagKey<Block> of(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Stuff.id(name));
    }

    interface Items {
        TagKey<Item> BOTTLES = of("bottles");
        TagKey<Item> BARRELS = of("barrels");
        TagKey<Item> PLACEABLE = of("placeable");
        TagKey<Item> DRINK_RECEPTICALS = of("drink_recepticals");
        TagKey<Item> SUITABLE_HOT_DRINK_RECEPTICALS = of("suitable_hot_drink_recepticals");
        TagKey<Item> SUITABLE_ALCOHOLIC_DRINK_RECEPTICALS = of("suitable_alcoholic_drink_recepticals");
        TagKey<Item> CAN_GO_INTO_PAPER_BAG = of("can_go_into_paper_bag");
        TagKey<Item> DRUG_CROP_SEEDS = of("drug_crop_seeds");

        static TagKey<Item> of(String name) {
            return TagKey.of(RegistryKeys.ITEM, Stuff.id(name));
        }
    }

    interface Entities {
        TagKey<EntityType<?>> MULTIPLE_ENTITY_HALLUCINATIONS = of("multiple_entity_hallucinations");
        TagKey<EntityType<?>> SINGLE_ENTITY_HALLUCINATIONS = of("single_entity_hallucinations");

        static TagKey<EntityType<?>> of(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Stuff.id(name));
        }
    }

    static void bootstrap() {}
}