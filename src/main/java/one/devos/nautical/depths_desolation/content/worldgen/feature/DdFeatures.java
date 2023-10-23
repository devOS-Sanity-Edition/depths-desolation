package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.deepgen.DeepGenFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.deepgen.DeepGenFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.lightroot.LightrootFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.lightroot.LightrootFeatureConfiguration;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeature;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;

public class DdFeatures {
	public static final SnowifyFeature SNOWIFY = register("snowify", new SnowifyFeature(SnowifyFeatureConfiguration.CODEC));
	public static final DecoratedGeodeFeature DECORATED_GEODE = register("decorated_geode", new DecoratedGeodeFeature(DecoratedGeodeConfiguration.CODEC));
	public static final LightrootFeature LIGHTROOT = register("lightroot", new LightrootFeature(LightrootFeatureConfiguration.CODEC));
	public static final DeepGenFeature DEEP_GEN = register("deep_gen", new DeepGenFeature(DeepGenFeatureConfiguration.CODEC));

	private static <C extends FeatureConfiguration, T extends Feature<C>> T register(String name, T feature) {
		return Registry.register(BuiltInRegistries.FEATURE, DepthsAndDesolation.id(name), feature);
	}

	public static void init() {
	}
}
