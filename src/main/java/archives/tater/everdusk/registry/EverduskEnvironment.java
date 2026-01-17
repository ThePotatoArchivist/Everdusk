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

    public static final EnvironmentAttribute<Boolean> OVERRIDE_SHADING = register(
            "visual/override_shading",
            EnvironmentAttribute.builder(AttributeTypes.BOOLEAN)
                    .defaultValue(false)
                    .notPositional()
                    .syncable()
    );

    public static final EnvironmentAttribute<Float> SECTION_REFRESH_FREQUENCY = register(
            "visual/section_refresh_frequency",
            EnvironmentAttribute.builder(AttributeTypes.FLOAT)
                    .defaultValue(0f)
                    .notPositional()
                    .syncable()
    );

    public static final EnvironmentAttribute<Float> SHADE_DOWN = registerShade(Direction.DOWN);
    public static final EnvironmentAttribute<Float> SHADE_UP = registerShade(Direction.UP);
    public static final EnvironmentAttribute<Float> SHADE_NORTH = registerShade(Direction.NORTH);
    public static final EnvironmentAttribute<Float> SHADE_SOUTH = registerShade(Direction.SOUTH);
    public static final EnvironmentAttribute<Float> SHADE_WEST = registerShade(Direction.WEST);
    public static final EnvironmentAttribute<Float> SHADE_EAST = registerShade(Direction.EAST);

    public static EnvironmentAttribute<Float> getShade(Direction direction) {
        return switch (direction) {
            case DOWN -> SHADE_DOWN;
            case UP -> SHADE_UP;
            case NORTH -> SHADE_NORTH;
            case SOUTH -> SHADE_SOUTH;
            case WEST -> SHADE_WEST;
            case EAST -> SHADE_EAST;
        };
    }

    public static void init() {

    }
}
