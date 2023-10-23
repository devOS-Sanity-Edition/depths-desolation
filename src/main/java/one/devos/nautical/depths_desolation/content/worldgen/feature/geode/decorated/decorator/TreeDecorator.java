package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import java.util.List;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import one.devos.nautical.depths_desolation.util.ConfiguredFeatureProvider;
import one.devos.nautical.depths_desolation.util.FloodFillPlane;

public class TreeDecorator extends GeodeDecorator {
	public static final Codec<TreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ConfiguredFeatureProvider.CODEC.fieldOf("features").forGetter(trees -> trees.features),
			ExtraCodecs.POSITIVE_INT.fieldOf("max_placements").orElse(Integer.MAX_VALUE).forGetter(trees -> trees.max)
	).apply(instance, TreeDecorator::new));

	public final ConfiguredFeatureProvider features;
	public final int max;

	public TreeDecorator(ConfiguredFeatureProvider features, int max) {
		super(Type.TREES);
		this.features = features;
		this.max = max;
	}

	@Override
	public void decorate(FeaturePlaceContext<DecoratedGeodeConfiguration> context, FloodFillPlane plane) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		List<BlockPos> positions = max == 1 ? plane.positions : outToIn(plane.positions, plane.center);
		int placed = 0;
		for (BlockPos pos : positions) {
			ConfiguredFeature<?, ?> tree = this.features.select(level.registryAccess(), random);
			if (tree == null) {
				continue;
			}
			BlockPos onFloor = moveToFloor(level, pos);
			if (tree.place(level, context.chunkGenerator(), random, onFloor)) {
				placed++;
				if (placed >= max) {
					break;
				}
			}
		}
	}
}
