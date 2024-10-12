package org.simon.stuff.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.simon.stuff.Stuff;
import org.simon.stuff.client.effect.JointEffectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    private static final Identifier CANNABIS_OVERLAY_TEXTURE = new Identifier(Stuff.MOD_ID, "textures/misc/cannabis_overlay.png");

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/gui/DrawContext;)V", shift = At.Shift.BEFORE))
    private void renderCannabisOverlay(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (JointEffectManager.isEffectActive()) {
            float intensity = JointEffectManager.getEffectIntensity();
            renderOverlay(context, CANNABIS_OVERLAY_TEXTURE, intensity);
        }
    }

    private void renderOverlay(DrawContext context, Identifier texture, float opacity) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        context.drawTexture(texture, 0, 0, -90, 0.0F, 0.0F, context.getScaledWindowWidth(), context.getScaledWindowHeight(), context.getScaledWindowWidth(), context.getScaledWindowHeight());
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
