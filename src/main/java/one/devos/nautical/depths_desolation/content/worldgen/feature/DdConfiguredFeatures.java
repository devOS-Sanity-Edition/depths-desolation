package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.snowify.SnowifyFeatureConfiguration;

public class DdConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWIFY = ResourceKey.create(
			Registries.CONFIGURED_FEATURE, DepthsAndDesolation.id("snowify")
	);

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
		ctx.register(SNOWIFY, new ConfiguredFeature<>(
				DdFeatures.SNOWIFY, new SnowifyFeatureConfiguration(6)
		));
	}
}
