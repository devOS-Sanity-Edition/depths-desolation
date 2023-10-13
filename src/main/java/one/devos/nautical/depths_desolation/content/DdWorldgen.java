package one.devos.nautical.depths_desolation.content;

import one.devos.nautical.depths_desolation.content.worldgen.feature.DdFeatures;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdPlacedFeatures;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.GenerationStep;

import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;

public class DdWorldgen {
	public static void init() {
		DdFeatures.init();

		modifyBiomes();
	}

	private static void modifyBiomes() {
		BiomeModifications.addFeature(
				BiomeSelectors.all(),
				GenerationStep.Decoration.FLUID_SPRINGS, // right before vegetation
				DdPlacedFeatures.SNOWIFY
		);

	}

	public static boolean isOverworld(LevelReader reader) {
		// ResourceKey is interned, == works
		return reader instanceof Level level && level.dimension() == Level.OVERWORLD;
	}
}
