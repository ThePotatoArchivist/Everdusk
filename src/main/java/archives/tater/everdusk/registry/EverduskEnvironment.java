package archives.tater.everdusk.registry;

import archives.tater.everdusk.Everdusk;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeRange;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

public class EverduskEnvironment {

    private static <T> EnvironmentAttribute<T> register(final String path, final EnvironmentAttribute.Builder<T> attributeBuilder) {
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Everdusk.id(path), attributeBuilder.build());
    }

    private static EnvironmentAttribute<Float> registerShade(Direction direction) {
        return register(
                "visual/shade_" + direction.getName(),
                EnvironmentAttribute.builder(AttributeTypes.FLOAT)
                        .defaultValue(1f)
                        .spatiallyInterpolated()
                        .valueRange(AttributeRange.UNIT_FLOAT)
                        .syncable()
        );
    }

    public static final EnvironmentAttribute<Float> SUN_YAW = register(
            "visual/sun_yaw",
            EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES)
                    .defaultValue(0f)
                    .notPositional()
                    .spatiallyInterpolated()
                    .syncable()
    );

    public static final EnvironmentAttribute<Boolean> NO_SHADING = register(
            "visual/no_shading",
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
                    .notPositional()
                    .syncable()
    );

    public static final EnvironmentAttribute<Boolean> DIRECTIONAL_SKY_LIGHT = register(
            "visual/directional_sky_light",
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
                    .notPositional()
                    .syncable()
    );

    public static void init() {

    }
}
