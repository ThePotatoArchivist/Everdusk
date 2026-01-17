package archives.tater.everdusk;

import archives.tater.everdusk.mixin.client.LevelRendererAccessor;
import archives.tater.everdusk.registry.EverduskEnvironment;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;

public class EverduskClient implements ClientModInitializer {
	public static final RenderStateDataKey<Float> SUN_YAW = RenderStateDataKey.create(() -> "everdusk:sun_yaw");

	public static int withSky(final int coords, final int sky) {
		return coords & 0xF0 | sky << 20;
	}

	public static int getModifiedLight(int original, Direction direction) {
        var level = Minecraft.getInstance().level;
        if (level == null) return original;

        float shade = level.environmentAttributes().getDimensionValue(EverduskEnvironment.getShade(direction));
        if (shade == 1f) return original;

        return withSky(original, (int) (shade * LightCoordsUtil.sky(original)));
    }

    @Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ClientTickEvents.END_LEVEL_TICK.register(level -> {
			var sectionRefresh = level.environmentAttributes().getDimensionValue(EverduskEnvironment.SECTION_REFRESH_FREQUENCY).intValue();
			if (sectionRefresh == 0) return;
			if (level.getOverworldClockTime() % sectionRefresh == 0)
				for (var section : ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getViewArea().sections) {
					section.setDirty(false);
				}
		});
	}
}