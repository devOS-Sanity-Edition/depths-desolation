package one.devos.nautical.depths_desolation.feature.snowify;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SnowifyFeature extends Feature<SnowifyFeatureConfiguration> {
	public SnowifyFeature(Codec<SnowifyFeatureConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SnowifyFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		int depth = context.config().depth();
		BlockPos max = origin.offset(15, 0, 15);
		for (BlockPos pos : BlockPos.betweenClosed(origin, max)) {
			pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
			freezeSupportingBlock(level, pos);
			for (int i = 0; i <= depth; i++) {
				BlockPos above = pos.above(i);
				if (level.getBlockState(above).isAir()) {
					Block block = i == depth ? Blocks.SNOW : Blocks.SNOW_BLOCK;
					level.setBlock(above, block.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		}

		return true;
	}

	protected void freezeSupportingBlock(WorldGenLevel level, BlockPos pos) {
		BlockPos below = pos.below();
		BlockState state = level.getBlockState(below);
		if (state.is(Blocks.LAVA)) {
			level.setBlock(below, Blocks.OBSIDIAN.defaultBlockState(), Block.UPDATE_CLIENTS);
		} else if (state.is(Blocks.WATER)) {
			level.setBlock(below, Blocks.ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
		} else if (state.is(Blocks.GRASS_BLOCK)) {
			BlockState newState = state.setValue(GrassBlock.SNOWY, true);
			level.setBlock(below, newState, Block.UPDATE_CLIENTS);
		}
	}
}
