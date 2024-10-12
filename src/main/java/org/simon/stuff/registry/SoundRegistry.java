package org.simon.stuff.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;

import java.util.HashMap;
import java.util.Map;

public class SoundRegistry {
    public static final Map<String, SoundEvent> SOUND_EVENTS = new HashMap<>();

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Stuff.MOD_ID, name);
        SoundEvent soundEvent = SoundEvent.of(id);
        SOUND_EVENTS.put(name, soundEvent);
        return Registry.register(Registries.SOUND_EVENT, id, soundEvent);
    }

    public static final SoundEvent AMONG_US_SOUND = registerSoundEvent("music_disc.among_us");
    public static final SoundEvent BITCHES_SOUND = registerSoundEvent("music_disc.bitches");
    public static final SoundEvent HAMBURG_SOUND = registerSoundEvent("music_disc.hamburg");
    public static final SoundEvent ITS_LIT_SOUND = registerSoundEvent("music_disc.its_lit");
    public static final SoundEvent LOCKDOWN_SOUND = registerSoundEvent("music_disc.lockdown");
    public static final SoundEvent DOTA3_SOUND = registerSoundEvent("music_disc.dota3");
    public static final SoundEvent ARSCHRAP_SOUND = registerSoundEvent("music_disc.arschrap");
    public static final SoundEvent MIAU_MIAU_MIAU = registerSoundEvent("music_disc.miau_miau_miau");
    public static final SoundEvent DUMPTRUCK = registerSoundEvent("music_disc.dumptruck");
    public static final SoundEvent SPAST = registerSoundEvent("music_disc.spast");

    public static void registerSounds() {
        Stuff.LOGGER.info("Registering sounds for " + Stuff.MOD_ID);
    }
}
