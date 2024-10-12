package org.simon.stuff.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.effect.CustomEffect;

public class EffectRegistry {
    public static final StatusEffect CUSTOM_EFFECT = new CustomEffect();

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Stuff.MOD_ID, "custom_effect"), CUSTOM_EFFECT);
    }
}