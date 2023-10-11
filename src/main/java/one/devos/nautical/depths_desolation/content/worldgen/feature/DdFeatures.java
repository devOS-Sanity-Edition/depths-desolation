package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave.SpawnCaveFeatureConfiguration;

public class DdFeatures {
	public static final SnowifyFeature SNOWIFY = register("snowify", new SnowifyFeature(SnowifyFeatureConfiguration.CODEC));
	public static final SpawnCaveFeature SPAWN_CAVE = register("spawn_cave", new SpawnCaveFeature(SpawnCaveFeatureConfiguration.CODEC));

	private static <C extends FeatureConfiguration, T extends Feature<C>> T register(String name, T feature) {
		return Registry.register(BuiltInRegistries.FEATURE, DepthsAndDesolation.id(name), feature);
	}

	public static void init() {
	}
}
