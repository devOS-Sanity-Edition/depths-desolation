package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import com.google.common.collect.AbstractIterator;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Iterator;

public abstract class DecoratedGeodeFeature<T extends DecoratedGeodeConfiguration> extends Feature<T> {
	public DecoratedGeodeFeature(Codec<T> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<T> context) {
		GeodePlaceContext geodeCtx = new GeodePlaceContext(context, context.config().geodeConfig, points -> {
			BlockPos pos = points.get(0); // guaranteed non-empty
			this.decorate(context, pos);
		});

		return Feature.GEODE.place(geodeCtx);
	}

	public abstract void decorate(FeaturePlaceContext<T> ctx, BlockPos inGeode);

	public static void moveToFloor(MutableBlockPos pos, WorldGenLevel level) {
		while (level.getBlockState(pos).canBeReplaced() && pos.getY() >= level.getMinBuildHeight()) {
			pos.move(Direction.DOWN);
		}
		pos.move(Direction.UP);
	}

	public static Iterable<MutableBlockPos> spiralAroundInAir(WorldGenLevel level, BlockPos center, int radius) {
		Iterator<MutableBlockPos> itr = BlockPos.spiralAround(center, radius, Direction.SOUTH, Direction.EAST).iterator();
		return () -> new AbstractIterator<>() {
			@Override
			protected MutableBlockPos computeNext() {
				MutableBlockPos pos = itr.next();
				if (level.getBlockState(pos).canBeReplaced()) {
					return pos;
				} else if (itr.hasNext()) {
					return computeNext();
				} else {
					return endOfData();
				}
			}
		};
	}
}
