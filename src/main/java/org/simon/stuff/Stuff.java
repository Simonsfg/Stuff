package org.simon.stuff;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import org.simon.stuff.advancement.StuffCriteria;
import org.simon.stuff.block.StuffBlocks;
import org.simon.stuff.block.entity.StuffBlockEntities;
import org.simon.stuff.command.StuffCommands;
import org.simon.stuff.config.JsonConfig;
import org.simon.stuff.config.StuffConfig;
import org.simon.stuff.entity.StuffEntities;
import org.simon.stuff.entity.drug.DrugProperties;
import org.simon.stuff.item.ItemGroupRegistry;
import org.simon.stuff.item.StuffItems;
import org.simon.stuff.particle.StuffParticles;
import org.simon.stuff.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class Stuff implements ModInitializer {

    public static final String MOD_ID = "stuff";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Supplier<JsonConfig.Loader<StuffConfig>> CONFIG_LOADER = JsonConfig.create("stuff.json", StuffConfig::new);

    public static Supplier<Optional<DrugProperties>> globalDrugProperties = Optional::empty;
    public static Supplier<Optional<HitResult>> crossHairTarget = Optional::empty;

    public static Optional<DrugProperties> getGlobalDrugProperties() {
        return globalDrugProperties.get();
    }

    public static Optional<HitResult> getCrossHairTarget() {
        return crossHairTarget.get();
    }

    public static JsonConfig.Loader<StuffConfig> getConfigLoader() {
        return CONFIG_LOADER.get();
    }

    public static StuffConfig getConfig() {
        return getConfigLoader().getData();
    }

    public static Identifier id(String name) {
        return new Identifier("stuff", name);
    }


    @Override   
    public void onInitialize() {
        LOGGER.info("Initializing Stuff mod");
        StuffRegistries.init();
        SoundRegistry.registerSounds();
        EntityRegistry.registerEntities();
        ItemRegistry.registerItems();
        EffectRegistry.registerEffects();
        ItemGroupRegistry.registerItemGroups();
        StuffItems.bootstrap();
        StuffBlocks.bootstrap();
        StuffEntities.bootstrap();
        StuffDamageTypes.bootstrap();
        StuffSounds.bootstrap();
        StuffBlockEntities.bootstrap();
        StuffParticles.bootstrap();
        StuffCriteria.bootstrap();
        StuffCommands.bootstrap();
    }
}



