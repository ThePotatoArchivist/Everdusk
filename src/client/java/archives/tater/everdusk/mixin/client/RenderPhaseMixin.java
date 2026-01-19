package archives.tater.everdusk.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import net.minecraft.client.renderer.RenderPipelines;

@Mixin(RenderPipelines.class)
public class RenderPhaseMixin {
    @Definition(id = "TERRAIN_SNIPPET", field = "Lnet/minecraft/client/renderer/RenderPipelines;TERRAIN_SNIPPET:Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;")
    @Definition(id = "buildSnippet", method = "Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;buildSnippet()Lcom/mojang/blaze3d/pipeline/RenderPipeline$Snippet;")
    @Expression("TERRAIN_SNIPPET = @(?).buildSnippet()")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static RenderPipeline.Builder modifyTerrainSnippet(RenderPipeline.Builder original) {
        return original
                .withUniform("LightDirection", UniformType.UNIFORM_BUFFER);
    }
}
