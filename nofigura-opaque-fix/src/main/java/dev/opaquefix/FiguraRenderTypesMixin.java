package dev.opaquefix;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.figuramc.figura.model.rendering.texture.FiguraRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * NoFigura ตั้ง render type หลักของโมเดลอวตาร์เป็น TRANSLUCENT เป็นค่าเริ่มต้น
 * (ดู ImmediateFiguraRenderer#setupRootCustomization) ทำให้โมเดลถูกวาดใน pass โปร่งใสของเชดเดอร์
 * และดูโปร่งใสเมื่อด้านหลังเป็นท้องฟ้า
 *
 * ตัวนี้สลับ TRANSLUCENT -> CUTOUT และ TRANSLUCENT_CULL -> CUTOUT_CULL เมื่อใช้เชดเดอร์อยู่
 */
@Mixin(value = FiguraRenderTypes.class, remap = false)
public abstract class FiguraRenderTypesMixin {

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void opaquefix$forceCutout(Identifier id, CallbackInfoReturnable<RenderType> cir) {
        FiguraRenderTypes self = (FiguraRenderTypes) (Object) this;

        FiguraRenderTypes replacement;
        if (self == FiguraRenderTypes.TRANSLUCENT) {
            replacement = FiguraRenderTypes.CUTOUT;
        } else if (self == FiguraRenderTypes.TRANSLUCENT_CULL) {
            replacement = FiguraRenderTypes.CUTOUT_CULL;
        } else {
            return;
        }

        if (ShaderCheck.shouldForceOpaque()) {
            cir.setReturnValue(replacement.get(id));
        }
    }
}
