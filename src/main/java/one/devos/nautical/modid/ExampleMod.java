package one.devos.nautical.modid;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.levelgen.GenerationStep;

import one.devos.nautical.modid.feature.ModFeatures;
import one.devos.nautical.modid.feature.ModPlacedFeatures;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String ID = "modid";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		ModFeatures.init();
		BiomeModifications.addFeature(
				BiomeSelectors.all(),
				GenerationStep.Decoration.FLUID_SPRINGS, // right before vegetation
				ModPlacedFeatures.SNOWIFY
		);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
