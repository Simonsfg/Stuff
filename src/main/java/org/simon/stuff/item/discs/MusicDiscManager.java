package org.simon.stuff.item.discs;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import org.simon.stuff.Stuff;
import org.simon.stuff.registry.SoundRegistry;

import java.util.ArrayList;
import java.util.List;

public class MusicDiscManager {
    public static final List<Item> MUSIC_DISC_ITEMS = new ArrayList<>();
    private static final List<MusicDiscInfo> MUSIC_DISCS = new ArrayList<>();

    static {
        // Add all your music discs here
        addMusicDisc("among_us", SoundRegistry.AMONG_US_SOUND, 101);
        addMusicDisc("bitches", SoundRegistry.BITCHES_SOUND, 285);
        addMusicDisc("hamburg", SoundRegistry.HAMBURG_SOUND, 130);
        addMusicDisc("its_lit", SoundRegistry.ITS_LIT_SOUND, 90);
        addMusicDisc("lockdown", SoundRegistry.LOCKDOWN_SOUND, 131);
        addMusicDisc("dota3", SoundRegistry.DOTA3_SOUND, 201);
        addMusicDisc("arschrap", SoundRegistry.ARSCHRAP_SOUND, 74);
        addMusicDisc("miau_miau_miau", SoundRegistry.MIAU_MIAU_MIAU, 104);
        addMusicDisc("dumptruck", SoundRegistry.DUMPTRUCK, 123);
        addMusicDisc("spast", SoundRegistry.SPAST, 54);
    }

    private static void addMusicDisc(String name, SoundEvent sound, int lengthInSeconds) {
        MUSIC_DISCS.add(new MusicDiscInfo(name, sound, lengthInSeconds));
    }

    public static void registerMusicDiscs() {
        for (MusicDiscInfo disc : MUSIC_DISCS) {
            Item musicDisc = Registry.register(Registries.ITEM, 
                Stuff.id("music_disc_" + disc.name()),
                new BaseMusicDisc(7, disc.sound(), new Item.Settings(), disc.lengthInSeconds()));
            MUSIC_DISC_ITEMS.add(musicDisc);
        }
    }

    public static List<MusicDiscInfo> getMusicDiscs() {
        return new ArrayList<>(MUSIC_DISCS);
    }
}