package archives.tater.everdusk.mixin.client;

import archives.tater.everdusk.registry.EverduskEnvironment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
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
    private float modifyShade(float original, @Local(argsOnly = true) Direction direction, @Local(argsOnly = true) boolean shade) {
        return environmentAttributes.getDimensionValue(EverduskEnvironment.OVERRIDE_SHADING) && shade
                ? environmentAttributes.getDimensionValue(EverduskEnvironment.DIRECTION_SHADES.get(direction))
                : original;
    }
}
