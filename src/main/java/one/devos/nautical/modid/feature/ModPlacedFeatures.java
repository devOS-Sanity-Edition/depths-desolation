package one.devos.nautical.modid.feature;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import one.devos.nautical.modid.ExampleMod;

import java.util.List;

public class ModPlacedFeatures {
	public static final ResourceKey<PlacedFeature> SNOWIFY = ResourceKey.create(
			Registries.PLACED_FEATURE, ExampleMod.id("snowify")
	);

	public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> configured = ctx.lookup(Registries.CONFIGURED_FEATURE);
		ctx.register(SNOWIFY, new PlacedFeature(
				configured.getOrThrow(ModConfiguredFeatures.SNOWIFY),
				List.of(BiomeFilter.biome(), PlacementUtils.HEIGHTMAP)
		));
	}
}
