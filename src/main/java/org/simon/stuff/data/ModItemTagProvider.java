package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.discs.MusicDiscManager;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private void generateMusicDiscTags() {
        var musicDiscBuilder = getOrCreateTagBuilder(ItemTags.MUSIC_DISCS);
        var creeperDropBuilder = getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS);

        MusicDiscManager.MUSIC_DISC_ITEMS.forEach(disc -> {
            musicDiscBuilder.add(disc);
            creeperDropBuilder.add(disc);
        });
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        Stuff.LOGGER.info("Generating item tags");
        generateMusicDiscTags();
    }
}