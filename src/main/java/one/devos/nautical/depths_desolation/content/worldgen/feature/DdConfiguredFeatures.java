package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeatureConfiguration;

public class DdConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWIFY = create("snowify");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_CAVE = create("spawn_cave");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_CAVE_BONUS = create("spawn_cave_bonus");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
		ctx.register(SNOWIFY, new ConfiguredFeature<>(
				DdFeatures.SNOWIFY, new SnowifyFeatureConfiguration(6)
		));
		ctx.register(SPAWN_CAVE, new ConfiguredFeature<>(
				DdFeatures.SPAWN_CAVE, new SpawnCaveFeatureConfiguration(false)
		));
		ctx.register(SPAWN_CAVE_BONUS, new ConfiguredFeature<>(
				DdFeatures.SPAWN_CAVE, new SpawnCaveFeatureConfiguration(true)
		));
	}

	private static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, DepthsAndDesolation.id(name));
	}
}
