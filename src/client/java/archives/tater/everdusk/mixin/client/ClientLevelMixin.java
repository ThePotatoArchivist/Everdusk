package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.registry.EverduskEnvironment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @Shadow
    @Final
    private EnvironmentAttributeSystem environmentAttributes;

    @ModifyReturnValue(
            method = "getShade",
            at = @At("RETURN")
    )
    private float modifyShade(float original) {
        return environmentAttributes.getDimensionValue(EverduskEnvironment.OVERRIDE_SHADING) ? 1f : original;
    }
}
