package one.devos.nautical.modid.feature.snowify;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
		ChunkPos chunkPos = SectionPos.of(origin).chunk();
		BlockPos min = chunkPos.getBlockAt(0, 0, 0);
		BlockPos max = chunkPos.getBlockAt(15, 0, 15);
		for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
			pos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos);
			level.setBlock(pos, Blocks.SNOW_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
		}

		return true;
	}
}
