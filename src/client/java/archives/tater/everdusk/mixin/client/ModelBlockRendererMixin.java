package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.EverduskClient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {
    @ModifyExpressionValue(
            method = "tesselateWithoutAO",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$Cache;getLightCoords(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I")
    )
    private int modifyLight(int original, @Local(name = "direction") Direction direction) {
        return EverduskClient.getModifiedLight(original, direction);
    }

    @ModifyExpressionValue(
            method = "renderModelFaceFlat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$Cache;getLightCoords(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I")
    )
    private int modifyLight(int original, @Local(name = "quad") BakedQuad quad) {
        return EverduskClient.getModifiedLight(original, quad.direction());
    }

}
