package org.simon.stuff.entity.drug;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.entity.drug.type.CannabisDrug;

public class DrugType {
    public static final RegistryKey<Registry<DrugType>> REGISTRY_KEY = RegistryKey.ofRegistry(new Identifier(Stuff.MOD_ID, "drugs"));
    public static Registry<DrugType> REGISTRY;

    public static final DrugType CANNABIS = new DrugType();

    private final Identifier id;

    public DrugType() {
        this.id = new Identifier(Stuff.MOD_ID, "cannabis");
    }

    public Identifier id() {
        return id;
    }

    public Drug create() {
        if (this == CANNABIS) {
            return new CannabisDrug(0.99, 0.0001); // Adjust these values as needed
        }
        // Add other drug types here if needed
        throw new IllegalStateException("Unknown drug type: " + this);
    }

    public static void init() {
        // This method will be called during mod initialization
    }
}
