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
	public static final ResourceKey<PlacedFeature> SNOWIFY = create("snowify");
	public static final ResourceKey<PlacedFeature> LIGHTROOT = create("lightroot");

	public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = ctx.lookup(Registries.CONFIGURED_FEATURE);
		ctx.register(SNOWIFY, new PlacedFeature(
				configured.getOrThrow(DdConfiguredFeatures.SNOWIFY),
				List.of(BiomeFilter.biome(), PlacementUtils.HEIGHTMAP)
		));
		ctx.register(LIGHTROOT, new PlacedFeature(
				configured.getOrThrow(DdConfiguredFeatures.LIGHTROOT),
				List.of(BiomeFilter.biome())
		));
	}

	private static ResourceKey<PlacedFeature> create(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, DepthsAndDesolation.id(name));
	}
}
