package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import one.devos.nautical.depths_desolation.util.FloodFillPlane;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class GeodeDecorator {
	public static final Codec<GeodeDecorator> CODEC = Type.CODEC.dispatch(decorator -> decorator.type, Type::codec);

	public final Type type;

	public GeodeDecorator(Type type) {
		this.type = type;
	}

	public abstract void decorate(FeaturePlaceContext<DecoratedGeodeConfiguration> context, FloodFillPlane plane);

	public static List<BlockPos> outToIn(List<BlockPos> list, BlockPos center) {
		List<BlockPos> newList = new ArrayList<>(list);
		newList.sort(Comparator.comparingDouble(pos -> -pos.distSqr(center)));
		return newList;
	}

	public static BlockPos moveToSurface(WorldGenLevel level, BlockPos pos, Direction direction, Predicate<BlockState> predicate) {
		MutableBlockPos mutable = pos.mutable();
		while (!predicate.test(level.getBlockState(mutable))) {
			mutable.move(direction);
		}
		return mutable.move(direction, -1);
	}

	public static BlockPos moveToFloor(WorldGenLevel level, BlockPos pos) {
		return moveToSurface(level, pos, Direction.DOWN, state -> !state.canBeReplaced());
	}

	public enum Type implements StringRepresentable {
		TREES(() -> TreeDecorator.CODEC),
		FLOOR_DECOR(() -> FloorDecorDecorator.CODEC);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		private final Supplier<Codec<? extends GeodeDecorator>> codec;

		Type(Supplier<Codec<? extends GeodeDecorator>> codec) {
			this.codec = codec;
		}

		public Codec<? extends GeodeDecorator> codec() {
			return codec.get();
		}

		@Override
		@NotNull
		public String getSerializedName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
}
