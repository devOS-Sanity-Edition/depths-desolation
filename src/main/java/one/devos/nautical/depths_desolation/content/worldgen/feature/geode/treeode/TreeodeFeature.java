package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Blocks;
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
	public TreeodeFeature(Codec<TreeodeConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public void decorate(FeaturePlaceContext<TreeodeConfiguration> ctx, BlockPos inGeode) {
		WorldGenLevel level = ctx.level();
		ChunkGenerator chunkGen = ctx.chunkGenerator();
		RandomSource rand = ctx.random();
		ConfiguredFeature<?, ?> treeFeature = ctx.config().treeFeature.value();

		for (MutableBlockPos pos : spiralAroundInAir(level, inGeode, 5)) {
			moveToFloor(pos, level);
			boolean placed = treeFeature.place(level, chunkGen, rand, pos);
			if (placed) {
				break;
			}
		}
	}
}
