package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.client.DirectionalLighting;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.LightmapRenderState;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private LightmapRenderState lightmapRenderState;

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @Shadow
    @Final
    private Camera mainCamera;

    @Inject(
            method = "renderLevel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Lightmap;update(Lnet/minecraft/client/renderer/state/LightmapRenderState;)V")
    )
    private void updateLightDirection(DeltaTracker deltaTracker, CallbackInfo ci, @Local(name = "deltaPartialTick") float deltaPartialTick) {
        var lightDirection = DirectionalLighting.getLightVector(deltaPartialTick, mainCamera.attributeProbe());
        try (var view = RenderSystem.getDevice().createCommandEncoder().mapBuffer(DirectionalLighting.LIGHT_DIRECTION_UBO.currentBuffer(), false, true)) {
            Std140Builder.intoBuffer(view.data()).putVec3(lightDirection);
        }
    }

    @Inject(
            method = "close",
            at = @At("TAIL")
    )
    private void closeUniforms(CallbackInfo ci) {
        DirectionalLighting.LIGHT_DIRECTION_UBO.close();
    }
}
