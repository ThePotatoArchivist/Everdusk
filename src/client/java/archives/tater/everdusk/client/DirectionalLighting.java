package archives.tater.everdusk.client;

import archives.tater.everdusk.registry.EverduskEnvironment;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.state.LightmapRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributeProbe;
import net.minecraft.world.attribute.EnvironmentAttributes;

import org.joml.Vector3f;

public class DirectionalLighting {
    public static final int LIGHT_DIRECTION_UBO_SIZE = new Std140SizeCalculator().putIVec3().get();
    public static final MappableRingBuffer LIGHT_DIRECTION_UBO = new MappableRingBuffer(() -> "Light Direction UBO", GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_MAP_WRITE, LIGHT_DIRECTION_UBO_SIZE);
    public static final Lightmap NO_SKY_LIGHTMAP = new Lightmap();

    public static void updateLightmap(LightmapRenderState state) {
        var skyFactor = state.skyFactor;
        state.skyFactor = 0;
        NO_SKY_LIGHTMAP.update(state);
        state.skyFactor = skyFactor;
    }

    public static Vector3f getLightVector(float deltaPartialTick, EnvironmentAttributeProbe attributes) {
        var sunYaw = attributes.getValue(EverduskEnvironment.SUN_YAW, deltaPartialTick) * Mth.DEG_TO_RAD;
        var sunAngle = attributes.getValue(EnvironmentAttributes.SUN_ANGLE, deltaPartialTick) * Mth.DEG_TO_RAD;
        return new Vector3f(0, 1, 0).rotateZ(sunAngle).rotateY(sunYaw);
    }
}
