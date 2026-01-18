package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.client.DirectionalLighting;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.gui.components.DebugScreenOverlay;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @ModifyExpressionValue(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;levelLightmap()Lcom/mojang/blaze3d/textures/GpuTextureView;")
    )
    private GpuTextureView replaceViewedLightmap(GpuTextureView original) {
        return DirectionalLighting.NO_SKY_LIGHTMAP.getTextureView();
    }
}
