package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.client.DirectionalLighting;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;

@Mixin(ChunkSectionsToRender.class)
public class ChunkSectionsToRenderMixin {
    @Definition(id = "bindTexture", method = "Lcom/mojang/blaze3d/systems/RenderPass;bindTexture(Ljava/lang/String;Lcom/mojang/blaze3d/textures/GpuTextureView;Lcom/mojang/blaze3d/textures/GpuSampler;)V")
    @Definition(id = "lightmap", method = "Lnet/minecraft/client/renderer/GameRenderer;lightmap()Lcom/mojang/blaze3d/textures/GpuTextureView;")
    @Expression("?.bindTexture(?, ?.lightmap(), ?)")
    @WrapOperation(
            method = "renderGroup",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private void bindDirectionLightmaps(RenderPass instance, String s, GpuTextureView gpuTextureView, GpuSampler gpuSampler, Operation<Void> original) {
        original.call(instance, s, gpuTextureView, gpuSampler);
        instance.setUniform("LightDirection", DirectionalLighting.LIGHT_DIRECTION_UBO.currentBuffer());
    }
}
