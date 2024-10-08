package org.simon.stuff.item;

import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;

public class BaseMusicDisc extends MusicDiscItem {
    public BaseMusicDisc(int comparatorOutput, SoundEvent sound, Settings settings, int lengthInSeconds) {
        super(comparatorOutput, sound, settings.rarity(Rarity.RARE).maxCount(1), lengthInSeconds);
    }
}