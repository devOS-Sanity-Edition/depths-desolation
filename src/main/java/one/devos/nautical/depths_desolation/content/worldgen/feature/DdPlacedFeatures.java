package one.devos.nautical.depths_desolation.content.worldgen.feature;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.TreeodeType;

public class DdPlacedFeatures {
	public static final ResourceKey<PlacedFeature> SNOWIFY = create("snowify");
	public static final ResourceKey<PlacedFeature> LIGHTROOT = create("lightroot");
	public static final ResourceKey<PlacedFeature> DEEP_GEN = create("deep_gen");

	public static final Map<TreeodeType, ResourceKey<PlacedFeature>> BUILTIN_TREEODES = Arrays.stream(TreeodeType.values())
			.collect(Collectors.toMap(Function.identity(), type -> create(type + "_treeode")));

	public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = ctx.lookup(Registries.CONFIGURED_FEATURE);
		ctx.register(SNOWIFY, new PlacedFeature(
				configured.getOrThrow(DdConfiguredFeatures.SNOWIFY),
				List.of(BiomeFilter.biome(), PlacementUtils.HEIGHTMAP)
		));
		BUILTIN_TREEODES.forEach((type, key) -> ctx.register(key, new PlacedFeature(
				configured.getOrThrow(DdConfiguredFeatures.BUILTIN_TREEODES.get(type)),
				List.of(
						RarityFilter.onAverageOnceEvery(24),
						InSquarePlacement.spread(),
						HeightRangePlacement.uniform(
								VerticalAnchor.aboveBottom(30),
								VerticalAnchor.belowTop(25)
						),
						BiomeFilter.biome()
				)
		)));
	}

	private static ResourceKey<PlacedFeature> create(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, DepthsAndDesolation.id(name));
	}
}
