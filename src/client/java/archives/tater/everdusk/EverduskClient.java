package archives.tater.everdusk;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class EverduskClient implements ClientModInitializer {
	public static final RenderStateDataKey<Float> SUN_YAW = RenderStateDataKey.create(() -> "everdusk:sun_yaw");

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ClientTickEvents.END_LEVEL_TICK.register(level -> {
		});
	}
}