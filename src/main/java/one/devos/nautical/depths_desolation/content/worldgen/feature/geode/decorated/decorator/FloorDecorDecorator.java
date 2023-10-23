package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import java.util.List;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import one.devos.nautical.depths_desolation.util.FloodFillPlane;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FloorDecorDecorator extends GeodeDecorator {
	public static final float DECOR_CHANCE = 1 / 2f;

	public static final Codec<FloorDecorDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			BlockStateProvider.CODEC.listOf().fieldOf("providers").forGetter(decorator -> decorator.providers)
	).apply(instance, FloorDecorDecorator::new));

	public final List<BlockStateProvider> providers;

	public FloorDecorDecorator(List<BlockStateProvider> providers) {
		super(Type.FLOOR_DECOR);
		this.providers = providers;
	}

	@Override
	public void decorate(FeaturePlaceContext<DecoratedGeodeConfiguration> context, FloodFillPlane plane) {
		if (this.providers.isEmpty())
			return;

		WorldGenLevel level = context.level();
		RandomSource rand = context.random();
		for (BlockPos pos : plane.positions) {
			pos = moveToFloor(level, pos);
			if (level.getBlockState(pos).isAir() && rand.nextFloat() < DECOR_CHANCE) {
				BlockStateProvider provider = Util.getRandom(providers, rand);
				BlockState state = provider.getState(rand, pos);
				if (state.canSurvive(level, pos)) {
					level.setBlock(pos, state, Block.UPDATE_ALL);
				}
			}
		}
	}
}
