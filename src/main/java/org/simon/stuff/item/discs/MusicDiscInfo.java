package org.simon.stuff.item.discs;

import net.minecraft.sound.SoundEvent;

public record MusicDiscInfo(String name, SoundEvent sound, int lengthInSeconds) {}