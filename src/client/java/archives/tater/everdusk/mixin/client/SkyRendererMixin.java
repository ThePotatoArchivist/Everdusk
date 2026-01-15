package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.EverduskClient;
import archives.tater.everdusk.HasSunYaw;
import archives.tater.everdusk.registry.EverduskEnvironment;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributeProbe;
import net.minecraft.world.level.MoonPhase;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin implements HasSunYaw {
    @Unique
    private float sunYaw;

    @Inject(
            method = "extractRenderState",
            at = @At("TAIL")
    )
    private void extractSunYaw(ClientLevel level, float partialTicks, Camera camera, SkyRenderState state, CallbackInfo ci, @Local(name = "attributeProbe") EnvironmentAttributeProbe attributes) {
        state.setData(EverduskClient.SUN_YAW, attributes.getValue(EverduskEnvironment.SUN_YAW, partialTicks) * Mth.DEG_TO_RAD);
    }

    @Definition(id = "rotation", method = "Lcom/mojang/math/Axis;rotation(F)Lorg/joml/Quaternionf;")
    @Definition(id = "sunAngle", local = @Local(type = float.class, name = "sunAngle", argsOnly = true))
    @Definition(id = "mulPose", method = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V")
    @Expression("?.mulPose(?.rotation(sunAngle))")
    @Inject(
            method = "renderSunMoonAndStars",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private void applySunYaw(PoseStack poseStack, float sunAngle, float moonAngle, float starAngle, MoonPhase moonPhase, float rainBrightness, float starBrightness, CallbackInfo ci) {
        poseStack.mulPose(Axis.YP.rotation(sunYaw));
    }

    @Inject(
            method = "renderSunriseAndSunset",
            at = @At(value = "INVOKE:FIRST", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V")
    )
    private void applySunsetYaw(PoseStack poseStack, float sunAngle, int sunriseAndSunsetColor, CallbackInfo ci) {
        poseStack.mulPose(Axis.YP.rotation(sunYaw));
    }

    @Override
    public void everdusk$setSunYaw(float sunYaw) {
        this.sunYaw = sunYaw;
    }
}
