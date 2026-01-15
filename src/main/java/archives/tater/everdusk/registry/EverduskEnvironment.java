package archives.tater.everdusk.registry;

import archives.tater.everdusk.Everdusk;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeRange;
import net.minecraft.world.attribute.AttributeTypes;
import net.minecraft.world.attribute.EnvironmentAttribute;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EverduskEnvironment {

    private static <T> EnvironmentAttribute<T> register(final String path, final EnvironmentAttribute.Builder<T> attributeBuilder) {
        return Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Everdusk.id(path), attributeBuilder.build());
    }

    public static final EnvironmentAttribute<Float> SUN_YAW = register(
            "visual/sun_yaw",
            EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES)
                    .defaultValue(0f)
                    .notPositional()
                    .spatiallyInterpolated()
                    .syncable()
    );

    public static final EnvironmentAttribute<Float> SECTION_REFRESH_FREQUENCY = register(
            "visual/section_refresh_frequency",
            EnvironmentAttribute.builder(AttributeTypes.FLOAT)
                    .defaultValue(0f)
                    .notPositional()
                    .syncable()
    );

    public static final Map<Direction, EnvironmentAttribute<Float>> DIRECTION_SHADES =
            Arrays.stream(Direction.values()).collect(Collectors.toMap(
                    Function.identity(),
                    direction -> register(
                            "visual/shade_" + direction.getName(),
                            EnvironmentAttribute.builder(AttributeTypes.ANGLE_DEGREES)
                                    .defaultValue(1f)
                                    .spatiallyInterpolated()
                                    .valueRange(AttributeRange.UNIT_FLOAT)
                                    .syncable()
                    )
            ));

    public static void init() {

    }
}
