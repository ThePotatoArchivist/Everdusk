package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.EverduskClient;
import archives.tater.everdusk.HasSunYaw;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.SkyRenderState;

import static java.util.Objects.requireNonNullElse;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(
            method = "lambda$addSkyPass$0",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSunriseAndSunset(Lcom/mojang/blaze3d/vertex/PoseStack;FI)V")
    )
    private static void setSunYaw(GpuBufferSlice skyFog, SkyRenderState state, SkyRenderer skyRenderer, CallbackInfo ci) {
        ((HasSunYaw) skyRenderer).everdusk$setSunYaw(requireNonNullElse(state.getData(EverduskClient.SUN_YAW), 0f));
    }
}
