/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package org.simon.stuff.entity.drug;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import org.simon.stuff.StuffSounds;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.drug.type.*;
import org.simon.stuff.Stuff;


public record DrugType (Identifier id, Function<DrugType, Drug> constructor) {
    public static final Registry<DrugType> REGISTRY = FabricRegistryBuilder.createSimple(RegistryKey.<DrugType>ofRegistry(Stuff.id("drugs"))).buildAndRegister();
    public static final DrugType CANNABIS = register("cannabis", type -> new CannabisDrug(1, 0.0002d));
    public static final DrugType ZERO = register("zero", type -> new SimpleDrug(type, 1, 0.0001d));
    public static final DrugType POWER = register("power", type -> new PowerDrug(0.95, 0.0001d));
    public static final DrugType HARMONIUM = register("harmonium", type -> new HarmoniumDrug(1, 0.0003d));

    public Drug create() {
        return constructor.apply(this);
    }

    static DrugType register(String name, Function<DrugType, Drug> constructor) {
        DrugType type = new DrugType(Stuff.id(name), constructor);
        StuffSounds.register("drug." + name);
        return Registry.register(REGISTRY, type.id(), type);
    }
}
