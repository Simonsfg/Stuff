package org.simon.stuff.client.render.shader;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import org.jetbrains.annotations.Nullable;

public class StuffShaders {
    @Nullable
    private static ShaderProgram renderTypeZeroMatterProgram;

    public static ShaderProgram getRenderTypeZeroMatterProgram() {
        return renderTypeZeroMatterProgram;
    }

    public static void bootstrap() {
        CoreShaderRegistrationCallback.EVENT.register((manager, shaderList) -> {
            shaderList.add(Pair.of(new ShaderProgram(new ModdedResourceFactory(manager, "stuff"), "rendertype_zero_matter", VertexFormats.POSITION_COLOR), program -> {
                renderTypeZeroMatterProgram = program;
            }));
        });
    }
}
