package org.simon.stuff;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;

public interface StuffTags {
    TagKey<Block> BARRELS = ofBlock("barrels");
    TagKey<Block> DRYING_TABLES = ofBlock("drying_tables");

    static TagKey<Block> ofBlock(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Stuff.id(name));
    }

    interface Items {
        TagKey<Item> BOTTLES = ofItem("bottles");
        TagKey<Item> BARRELS = ofItem("barrels");
        TagKey<Item> PLACEABLE = ofItem("placeable");
        TagKey<Item> DRINK_RECEPTICALS = ofItem("drink_recepticals");
        TagKey<Item> SUITABLE_HOT_DRINK_RECEPTICALS = ofItem("suitable_hot_drink_recepticals");
        TagKey<Item> SUITABLE_ALCOHOLIC_DRINK_RECEPTICALS = ofItem("suitable_alcoholic_drink_recepticals");
        TagKey<Item> CAN_GO_INTO_PAPER_BAG = ofItem("can_go_into_paper_bag");
        TagKey<Item> DRUG_CROP_SEEDS = ofItem("drug_crop_seeds");

        static TagKey<Item> ofItem(String name) {
            return TagKey.of(RegistryKeys.ITEM, Stuff.id(name));
        }
    }

    interface Entities {
        TagKey<EntityType<?>> MULTIPLE_ENTITY_HALLUCINATIONS = ofEntity("multiple_entity_hallucinations");
        TagKey<EntityType<?>> SINGLE_ENTITY_HALLUCINATIONS = ofEntity("single_entity_hallucinations");

        static TagKey<EntityType<?>> ofEntity(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Stuff.id(name));
        }
    }

    static void bootstrap() {}
}