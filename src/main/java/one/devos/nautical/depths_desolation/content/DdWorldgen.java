package one.devos.nautical.depths_desolation.content;

import one.devos.nautical.depths_desolation.content.worldgen.chunkgen.DdChunkGenerators;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdFeatures;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdPlacedFeatures;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;

import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class DdWorldgen {
	public static void init() {
		DdFeatures.init();
		DdChunkGenerators.init();

		modifyBiomes();
	}

	private static void modifyBiomes() {
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Decoration.FLUID_SPRINGS, // right before vegetation
				DdPlacedFeatures.SNOWIFY
		);
		DdPlacedFeatures.BUILTIN_TREEODES.values().forEach(treeode -> BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				Decoration.LOCAL_MODIFICATIONS, // same as amethyst geodes
				treeode
		));
	}

	public static boolean isOverworld(LevelReader reader) {
		// ResourceKey is interned, == works
		return reader instanceof Level level && level.dimension() == Level.OVERWORLD;
	}
}
