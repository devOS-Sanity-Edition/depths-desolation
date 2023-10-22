package one.devos.nautical.depths_desolation.content.worldgen.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.DdBlocks;
import one.devos.nautical.depths_desolation.content.blocks.LightBulbBlock;
import one.devos.nautical.depths_desolation.content.worldgen.dimensiontype.DdDimensionTypes;
import one.devos.nautical.depths_desolation.content.worldgen.feature.deepgen.DeepGenFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode.TreeodeConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode.TreeodeType;
import one.devos.nautical.depths_desolation.content.worldgen.feature.lightroot.LightrootFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;
import one.devos.nautical.depths_desolation.data.tags.TreeFeatureTags;
import one.devos.nautical.depths_desolation.util.ConfiguredFeatureProvider;

public class DdConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWIFY = create("snowify");

	public static final Map<TreeodeType, ResourceKey<ConfiguredFeature<?, ?>>> BUILTIN_TREEODES = Arrays.stream(TreeodeType.values())
			.collect(Collectors.toMap(Function.identity(), type -> create(type + "_treeode")));

	public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTROOT = create("lightroot");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_GEN = create("deep_gen");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
		ctx.register(SNOWIFY, new ConfiguredFeature<>(
				DdFeatures.SNOWIFY, new SnowifyFeatureConfiguration(6)
		));

		BUILTIN_TREEODES.forEach((type, key) -> ctx.register(key, treeode(type)));

		ctx.register(LIGHTROOT, new ConfiguredFeature<>(
				DdFeatures.LIGHTROOT, new LightrootFeatureConfiguration()
		));
		ctx.register(DEEP_GEN, new ConfiguredFeature<>(
				DdFeatures.DEEP_GEN, new DeepGenFeatureConfiguration(
				DdDimensionTypes.MIN_Y, -65
		)));
	}

	private static ConfiguredFeature<?, ?> treeode(TreeodeType type) {
		TagKey<ConfiguredFeature<?, ?>> treeTag = TreeFeatureTags.BY_TYPE.get(type);

		BlockState lightBulb = DdBlocks.LIGHT_BULB.defaultBlockState().setValue(LightBulbBlock.NATURAL, true);
		List<BlockState> bulbs = LightBulbBlock.AGE.getPossibleValues().stream()
				.map(age -> lightBulb.setValue(LightBulbBlock.AGE, age))
				.toList();

		return new ConfiguredFeature<>(DdFeatures.TREEODE, new TreeodeConfiguration(
				// shenanigans: https://gist.github.com/TropheusJ/69b8daa691bbdd9ae43b4d506ff33005
				new GeodeConfiguration(
						new GeodeBlockSettings(
								BlockStateProvider.simple(Blocks.AIR),
								BlockStateProvider.simple(DdBlocks.FULL_GRASS),
								BlockStateProvider.simple(Blocks.MOSS_BLOCK),
								BlockStateProvider.simple(Blocks.ROOTED_DIRT),
								BlockStateProvider.simple(Blocks.DIRT),
								bulbs,
								BlockTags.FEATURES_CANNOT_REPLACE,
								BlockTags.GEODE_INVALID_BLOCKS
						),
						new GeodeLayerSettings(6, 7.5, 9, 10.5),
						new GeodeCrackSettings(0.95, 2.0, 1),
						0.35,
						0.083,
						true,
						ConstantInt.of(5),
						ConstantInt.of(3),
						ConstantInt.of(1),
						-15,
						15,
						0.05,
						1
				),
				new ConfiguredFeatureProvider.Tag(treeTag),
				List.of(
						BlockStateProvider.simple(Blocks.DANDELION),
						BlockStateProvider.simple(Blocks.POPPY),
						BlockStateProvider.simple(Blocks.GRASS),
						BlockStateProvider.simple(Blocks.FERN),
						BlockStateProvider.simple(Blocks.MOSS_CARPET)
				)
		));
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, DepthsAndDesolation.id(name));
	}
}
