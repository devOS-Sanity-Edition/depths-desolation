package one.devos.nautical.depths_desolation.content.worldgen.feature;

import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.DdBlocks;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode.TreeodeConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeatureConfiguration;

public class DdConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWIFY = create("snowify");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_CAVE = create("spawn_cave");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_CAVE_BONUS = create("spawn_cave_bonus");

	public static final ResourceKey<ConfiguredFeature<?, ?>> TREEODE_OAK = create("oak_treeode");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = ctx.lookup(Registries.CONFIGURED_FEATURE);

		ctx.register(SNOWIFY, new ConfiguredFeature<>(
				DdFeatures.SNOWIFY, new SnowifyFeatureConfiguration(6)
		));
		ctx.register(SPAWN_CAVE, new ConfiguredFeature<>(
				DdFeatures.SPAWN_CAVE, new SpawnCaveFeatureConfiguration(false)
		));
		ctx.register(SPAWN_CAVE_BONUS, new ConfiguredFeature<>(
				DdFeatures.SPAWN_CAVE, new SpawnCaveFeatureConfiguration(true)
		));
		ctx.register(TREEODE_OAK, new ConfiguredFeature<>(
				DdFeatures.TREEODE, new TreeodeConfiguration(
				// shenanigans: https://gist.github.com/TropheusJ/69b8daa691bbdd9ae43b4d506ff33005
				new GeodeConfiguration(
						new GeodeBlockSettings(
								BlockStateProvider.simple(Blocks.AIR),
								BlockStateProvider.simple(DdBlocks.FULL_GRASS),
								BlockStateProvider.simple(Blocks.MOSS_BLOCK),
								BlockStateProvider.simple(Blocks.ROOTED_DIRT),
								BlockStateProvider.simple(Blocks.DIRT),
								List.of(
										Blocks.FERN.defaultBlockState(),
										Blocks.GRASS.defaultBlockState()
								),
								BlockTags.FEATURES_CANNOT_REPLACE,
								BlockTags.GEODE_INVALID_BLOCKS
						),
						new GeodeLayerSettings(6, 7.5, 9, 10.5),
						new GeodeCrackSettings(1, 2.0, 2),
						1,
						0.083,
						true,
						ConstantInt.of(5),
						ConstantInt.of(3),
						ConstantInt.of(1),
						-16,
						16,
						0.05,
						1
				),
				configuredFeatures.getOrThrow(TreeFeatures.OAK)
		)
		));
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, DepthsAndDesolation.id(name));
	}
}
