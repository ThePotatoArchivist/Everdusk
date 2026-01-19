package archives.tater.everdusk.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.world.level.dimension.DimensionType;

import org.joml.Vector3f;

@Mixin(Lighting.class)
public abstract class LightingMixin {
    @Unique
    private static final Vector3f STRAIGHT_LIGHT = new Vector3f(0f, 1f, 0f).rotateZ(95f);
    @Shadow
    protected abstract void updateBuffer(Lighting.Entry entry, Vector3f light0, Vector3f light1);

    /**
     * @author ThePotatoArchivist
     * @reason For testing purposes only, it will be removed later
     */
    @Overwrite
    public void updateLevel(final DimensionType.CardinalLightType type) {
        updateBuffer(Lighting.Entry.LEVEL, STRAIGHT_LIGHT, STRAIGHT_LIGHT);
    }
}
