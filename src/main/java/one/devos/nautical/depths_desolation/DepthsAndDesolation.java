package one.devos.nautical.depths_desolation;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.GenerationStep;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

import one.devos.nautical.depths_desolation.feature.DdFeatures;

import one.devos.nautical.depths_desolation.feature.DdPlacedFeatures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthsAndDesolation implements ModInitializer {
	public static final String ID = "depths_desolation";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		DdFeatures.init();
		BiomeModifications.addFeature(
				BiomeSelectors.all(),
				GenerationStep.Decoration.FLUID_SPRINGS, // right before vegetation
				DdPlacedFeatures.SNOWIFY
		);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static boolean isOverworld(Level level) {
		// ResourceKey is interned, == works
		return level.dimension() == Level.OVERWORLD;
	}
}
