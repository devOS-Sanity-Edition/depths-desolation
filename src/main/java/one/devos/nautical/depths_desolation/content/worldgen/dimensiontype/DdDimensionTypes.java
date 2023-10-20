package one.devos.nautical.depths_desolation.content.worldgen.dimensiontype;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import java.util.OptionalLong;

public class DdDimensionTypes {
	public static final ResourceKey<DimensionType> DESOLATE_OVERWORLD = ResourceKey.create(
			Registries.DIMENSION_TYPE, DepthsAndDesolation.id("overworld")
	);

	public static final int HEIGHT = 512 + 64;
	public static final int MAX_Y = 384;
	public static final int MIN_Y = MAX_Y - HEIGHT;

	public static void boostrap(BootstapContext<DimensionType> ctx) {
		// height is only difference
		ctx.register(DESOLATE_OVERWORLD, new DimensionType(
				OptionalLong.empty(),
				true,
				false,
				false,
				true,
				1.0,
				true,
				false,
				MIN_Y,
				HEIGHT,
				HEIGHT,
				BlockTags.INFINIBURN_OVERWORLD,
				BuiltinDimensionTypes.OVERWORLD_EFFECTS,
				0.0F,
				new DimensionType.MonsterSettings(
						false, true, UniformInt.of(0, 7), 0
				)
		));
	}
}
