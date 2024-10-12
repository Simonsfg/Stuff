package org.simon.stuff.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import org.simon.stuff.entity.drug.DrugType;

public class StuffRegistries {
    public static void init() {
        DrugType.REGISTRY = FabricRegistryBuilder.createSimple(DrugType.REGISTRY_KEY).buildAndRegister();
        Registry.register(DrugType.REGISTRY, DrugType.CANNABIS.id(), DrugType.CANNABIS);
    }
}