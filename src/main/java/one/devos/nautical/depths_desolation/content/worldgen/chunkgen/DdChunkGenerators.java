package one.devos.nautical.depths_desolation.content.worldgen.chunkgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;

public class DdChunkGenerators {
	public static void init() {
		Registry.register(
				BuiltInRegistries.CHUNK_GENERATOR,
				DepthsAndDesolation.id("desolate"),
				DesolateChunkGenerator.CODEC
		);
	}
}
