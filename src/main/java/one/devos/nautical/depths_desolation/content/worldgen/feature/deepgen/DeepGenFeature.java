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
		int firstOctave = -4;
		int amplitudes = 1;
		float horizontalScaling = 3;
		float verticalScaling = 1;
		float minSolidThreshold = 0;
		float maxSolidThreshold = 0.4f;

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

			double value = noise.getValue(pos.getX() / horizontalScaling, pos.getY() / verticalScaling, pos.getZ() / horizontalScaling);

			int dist = Math.min(distFromMin, distFromMax);
			if (dist < 10) {
				// 0 -> 1 from distance 10 -> 0
				double bias = Math.cos((dist * Mth.HALF_PI) / 10);
				value -= bias;
			}

			if (minSolidThreshold < value && value < maxSolidThreshold) {
				setBlock(level, pos, Blocks.STONE.defaultBlockState());
			}
		}
		return true;
	}
}
