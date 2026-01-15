package archives.tater.everdusk.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;

@Mixin(ModelBlockRenderer.class)
public class ClientLevelMixin {
    @ModifyExpressionValue(
            method = "renderModelFaceFlat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$Cache;getLightCoords(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I")
    )
    private int directionalLight(int original, @Local(name = "quad") BakedQuad quad) {
        return quad.direction() == Direction.EAST ? LightCoordsUtil.pack(LightCoordsUtil.block(original), LightCoordsUtil.sky(original) / 4) : original;
    }

    @ModifyExpressionValue(
            method = "tesselateWithoutAO",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$Cache;getLightCoords(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I")
    )
    private int directionalLight(int original, @Local(name = "direction") Direction direction) {
        return direction == Direction.EAST ? LightCoordsUtil.pack(LightCoordsUtil.block(original), LightCoordsUtil.sky(original) / 4) : original;
    }
}
