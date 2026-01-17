package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.EverduskClient;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.Direction;

@Mixin(targets = "net.minecraft.client.renderer.block.ModelBlockRenderer$AmbientOcclusionRenderStorage")
public class AmbientOcclusionRenderStorageMixin {
    @ModifyExpressionValue(
            method = "calculate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$Cache;getLightCoords(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I")
    )
    private int modifyLight(int original, @Local(argsOnly = true) Direction direction) {
        return EverduskClient.getModifiedLight(original, direction);
    }
}
