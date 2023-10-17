package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode;

import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class TreeodeFeature extends DecoratedGeodeFeature<TreeodeConfiguration> {
	public static final float DECOR_CHANCE = 1 / 2f;

	public TreeodeFeature(Codec<TreeodeConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public void decorate(FeaturePlaceContext<TreeodeConfiguration> ctx, BlockPos inGeode) {
		WorldGenLevel level = ctx.level();
		ChunkGenerator chunkGen = ctx.chunkGenerator();
		RandomSource rand = ctx.random();
		TreeodeConfiguration config = ctx.config();
		ConfiguredFeature<?, ?> treeFeature = config.treeFeature.value();

		for (MutableBlockPos pos : spiralAroundInAir(level, inGeode, 5)) {
			moveToFloor(pos, level);
			boolean placed = treeFeature.place(level, chunkGen, rand, pos);
			if (placed) {
				break;
			}
		}

		if (config.floorDecor.isEmpty())
			return;

		for (MutableBlockPos pos : spiralAroundInAir(level, inGeode, 5)) {
			moveToFloor(pos, level);
			if (level.getBlockState(pos).isAir() && rand.nextFloat() > DECOR_CHANCE) {
				BlockState state = Util.getRandom(config.floorDecor, rand);
				if (state.canSurvive(level, pos)) {
					setBlock(level, pos, state);
				}
			}
		}
	}
}
