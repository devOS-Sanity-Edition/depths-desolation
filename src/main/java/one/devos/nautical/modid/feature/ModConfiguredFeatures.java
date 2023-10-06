package one.devos.nautical.modid.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import one.devos.nautical.modid.ExampleMod;
import one.devos.nautical.modid.feature.snowify.SnowifyFeatureConfiguration;

public class ModConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWIFY = ResourceKey.create(
			Registries.CONFIGURED_FEATURE, ExampleMod.id("snowify")
	);

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
		ctx.register(SNOWIFY, new ConfiguredFeature<>(
				ModFeatures.SNOWIFY, new SnowifyFeatureConfiguration(6)
		));
	}
}
