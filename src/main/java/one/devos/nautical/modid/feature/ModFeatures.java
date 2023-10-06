package one.devos.nautical.modid.feature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import one.devos.nautical.modid.ExampleMod;
import one.devos.nautical.modid.feature.snowify.SnowifyFeature;
import one.devos.nautical.modid.feature.snowify.SnowifyFeatureConfiguration;

public class ModFeatures {
	public static final SnowifyFeature SNOWIFY = register("snowify", new SnowifyFeature(SnowifyFeatureConfiguration.CODEC));

	private static <C extends FeatureConfiguration, T extends Feature<C>> T register(String name, T feature) {
		return Registry.register(BuiltInRegistries.FEATURE, ExampleMod.id(name), feature);
	}

	public static void init() {
	}
}
