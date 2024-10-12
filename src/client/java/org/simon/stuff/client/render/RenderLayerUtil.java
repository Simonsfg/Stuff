package org.simon.stuff.client.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface RenderLayerUtil {
    static Optional<Identifier> getTexture(RenderLayer layer) {
        // Use reflection to access the private MultiPhase class and its methods
        try {
            Class<?> multiPhaseClass = Class.forName("net.minecraft.client.render.RenderLayer$MultiPhase");
            if (multiPhaseClass.isInstance(layer)) {
                Object phases = multiPhaseClass.getMethod("getPhases").invoke(layer);
                Object texture = phases.getClass().getField("texture").get(phases);
                return (Optional<Identifier>) texture.getClass().getMethod("getId").invoke(texture);
            }
        } catch (Exception e) {
            // Log the exception or handle it as appropriate for your use case
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
