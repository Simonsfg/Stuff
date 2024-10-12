package org.simon.stuff.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import org.simon.stuff.Stuff;
import org.simon.stuff.item.discs.MusicDiscManager;
import org.simon.stuff.item.discs.MusicDiscInfo;

import java.util.concurrent.CompletableFuture;

public class ModSoundProvider extends FabricDynamicRegistryProvider {
    public ModSoundProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        // Add all music disc sounds
        for (MusicDiscInfo disc : MusicDiscManager.getMusicDiscs()) {
            RegistryKey<SoundEvent> key = RegistryKey.of(RegistryKeys.SOUND_EVENT, Stuff.id("music_disc." + disc.name()));
            entries.add(key, SoundEvent.of(Stuff.id("music_disc." + disc.name())));
        }
    }

    @Override
    public String getName() {
        return "Sounds";
    }
}