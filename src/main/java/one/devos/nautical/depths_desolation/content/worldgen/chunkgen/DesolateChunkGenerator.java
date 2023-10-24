package one.devos.nautical.depths_desolation.content.worldgen.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import org.jetbrains.annotations.NotNull;

public class DesolateChunkGenerator extends NoiseBasedChunkGenerator {
	public static final Codec<DesolateChunkGenerator> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource),
							NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(NoiseBasedChunkGenerator::generatorSettings)
					)
					.apply(instance, instance.stable(DesolateChunkGenerator::new))
	);

	public DesolateChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
		super(biomeSource, settings);
	}

	@Override
	@NotNull
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}
}
