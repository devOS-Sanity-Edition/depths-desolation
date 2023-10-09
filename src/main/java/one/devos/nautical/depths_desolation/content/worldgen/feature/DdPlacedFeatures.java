package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

public class DdPlacedFeatures {
	public static final ResourceKey<PlacedFeature> SNOWIFY = ResourceKey.create(
			Registries.PLACED_FEATURE, DepthsAndDesolation.id("snowify")
	);

	public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = ctx.lookup(Registries.CONFIGURED_FEATURE);
		ctx.register(SNOWIFY, new PlacedFeature(
				configured.getOrThrow(DdConfiguredFeatures.SNOWIFY),
				List.of(BiomeFilter.biome(), PlacementUtils.HEIGHTMAP)
		));
	}
}
