package archives.tater.everdusk.client;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import net.minecraft.client.renderer.Lightmap;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.state.LightmapRenderState;

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
}
