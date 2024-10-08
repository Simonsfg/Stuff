package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.simon.stuff.Stuff;
import org.simon.stuff.registry.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        Stuff.LOGGER.info("Generating item tags");
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                .add(ItemRegistry.MUSIC_DISC_BITCHES)
                .add(ItemRegistry.MUSIC_DISC_ARSCHRAP)
                .add(ItemRegistry.MUSIC_DISC_HAMBURG)
                .add(ItemRegistry.MUSIC_DISC_LOCKDOWN)
                .add(ItemRegistry.MUSIC_DISC_ITS_LIT)
                .add(ItemRegistry.MUSIC_DISC_DOTA3)
                .add(ItemRegistry.MUSIC_DISC_AMONG_US);

        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ItemRegistry.MUSIC_DISC_BITCHES)
                .add(ItemRegistry.MUSIC_DISC_ARSCHRAP)
                .add(ItemRegistry.MUSIC_DISC_HAMBURG)
                .add(ItemRegistry.MUSIC_DISC_LOCKDOWN)
                .add(ItemRegistry.MUSIC_DISC_ITS_LIT)
                .add(ItemRegistry.MUSIC_DISC_DOTA3)
                .add(ItemRegistry.MUSIC_DISC_AMONG_US);
    }
}