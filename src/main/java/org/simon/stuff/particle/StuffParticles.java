package org.simon.stuff.particle;

import org.simon.stuff.Stuff;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface StuffParticles {
    ParticleType<ExhaledSmokeParticleEffect> EXHALED_SMOKE = register("exhaled_smoke", FabricParticleTypes.complex(ExhaledSmokeParticleEffect.FACTORY));

    static <T extends ParticleType<?>> T register(String name, T type) {
        return Registry.register(Registries.PARTICLE_TYPE, Stuff.id(name), type);
    }

    static void bootstrap() {}
}