package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Supplier;

public abstract class GeodeDecorator {
	public static final Codec<GeodeDecorator> CODEC = Type.CODEC.dispatch(decorator -> decorator.type, Type::codec);

	public final Type type;

	public GeodeDecorator(Type type) {
		this.type = type;
	}

	public abstract void decorate(WorldGenLevel level, BlockPos inGeode);

	public enum Type implements StringRepresentable {
		TREES(() -> Trees.CODEC),
		FLOOR_DECOR(() -> FloorDecor.CODEC);

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
