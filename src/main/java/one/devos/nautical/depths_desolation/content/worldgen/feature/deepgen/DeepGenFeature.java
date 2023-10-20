package one.devos.nautical.depths_desolation.content.worldgen.feature.deepgen;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class DeepGenFeature extends Feature<DeepGenFeatureConfiguration> {
	public DeepGenFeature(Codec<DeepGenFeatureConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<DeepGenFeatureConfiguration> context) {
		// weird noise math values
		int firstOctave = -4;
		int amplitudes = 1;
		float horizontalScaling = 3;
		float verticalScaling = 1;
		// noise is -1 <-> 1
		float minSolidThreshold = 0.1f; // places where noise is greater than this...
		float maxSolidThreshold = 1f; // and less than this...
		// are stone

		DeepGenFeatureConfiguration config = context.config();
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		BlockPos min = origin.atY(config.minY());
		BlockPos max = origin.atY(config.maxY()).offset(15, 0, 15);
		RandomSource random = context.random();
		random.setSeed(level.getSeed());
		NormalNoise noise = NormalNoise.create(random, firstOctave, amplitudes);
		for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
			int distFromMin = pos.getY() - config.minY();
			if (distFromMin == 0) {
				setBlock(level, pos, Blocks.DIAMOND_BLOCK.defaultBlockState());
				continue;
			}

			int distFromMax = config.maxY() - pos.getY();
			if (distFromMax == 0) {
				setBlock(level, pos, Blocks.DIAMOND_BLOCK.defaultBlockState());
				continue;
			}

			double solidness = noise.getValue(pos.getX() / horizontalScaling, pos.getY() / verticalScaling, pos.getZ() / horizontalScaling);

			int dist = Math.min(distFromMin, distFromMax);
			if (dist < 10) {
				// 0 -> 2 from distance 10 -> 0
				// -1: never actually hits 0, bottom layer
				double bias = Math.cos((((dist - 1) + 10) * Mth.PI) / 20) + 1;
				solidness = Math.min(1, solidness + bias);
			}

			if (solidness <= maxSolidThreshold && solidness >= minSolidThreshold) {
				setBlock(level, pos, Blocks.STONE.defaultBlockState());
			}
		}
		return true;
	}
}
