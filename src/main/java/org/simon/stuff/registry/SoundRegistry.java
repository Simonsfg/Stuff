package org.simon.stuff.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;

public class SoundRegistry {

    // Music discs
    public static final SoundEvent AMONG_US_SOUND = registerSoundEvent("among_us");
    public static final SoundEvent BITCHES_SOUND = registerSoundEvent("bitches");
    public static final SoundEvent HAMBURG_SOUND = registerSoundEvent("hamburg");
    public static final SoundEvent ITS_LIT_SOUND = registerSoundEvent("its_lit");
    public static final SoundEvent LOCKDOWN_SOUND = registerSoundEvent("lockdown");
    public static final SoundEvent DOTA3_SOUND = registerSoundEvent("dota3");
    public static final SoundEvent ARSCHRAP_SOUND = registerSoundEvent("arschrap");

    public static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Stuff.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        Stuff.LOGGER.info("Registering sounds for " + Stuff.MOD_ID);
    }
}
