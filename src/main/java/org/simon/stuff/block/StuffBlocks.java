package org.simon.stuff.block;

import org.simon.stuff.Stuff;
import org.simon.stuff.block.entity.StuffBlockEntities;

import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public interface StuffBlocks {
    CannabisPlantBlock CANNABIS = register("cannabis", new CannabisPlantBlock(BlockConstructionUtils.plant(BlockSoundGroup.GRASS)));

    Block GLITCH = register("glitch", new GlitchedBlock(Settings.create().mapColor(MapColor.BLACK).breakInstantly().hardness(0)
            .emissiveLighting(BlockConstructionUtils::always)
            .air().nonOpaque().noBlockBreakParticles().dropsNothing()
    ));


    static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, Stuff.id(name), block);
    }

    static void bootstrap() {
        StuffBlockEntities.bootstrap();

    }
}