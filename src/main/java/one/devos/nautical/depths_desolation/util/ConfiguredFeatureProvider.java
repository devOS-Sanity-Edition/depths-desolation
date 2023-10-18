package one.devos.nautical.depths_desolation.util;

import com.mojang.serialization.Codec;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public abstract class ConfiguredFeatureProvider {
	public static final Codec<ConfiguredFeatureProvider> CODEC = Type.CODEC.dispatch(
			provider -> provider.type, Type::codec
	);

	public final Type type;

	public ConfiguredFeatureProvider(Type type) {
		this.type = type;
	}

	@Nullable
	public abstract ConfiguredFeature<?, ?> select(RegistryAccess registries, RandomSource random);

	public enum Type implements StringRepresentable {
		DIRECT(() -> Direct.CODEC), // ID or inline
		TAG(() -> Tag.CODEC),
		ARRAY(() -> Array.CODEC);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		private final Supplier<Codec<? extends ConfiguredFeatureProvider>> codec;

		Type(Supplier<Codec<? extends ConfiguredFeatureProvider>> codec) {
			this.codec = codec;
		}

		public Codec<? extends ConfiguredFeatureProvider> codec() {
			return this.codec.get();
		}

		@Override
		@NotNull
		public String getSerializedName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}

	public static class Direct extends ConfiguredFeatureProvider {
		public static final Codec<Direct> CODEC = ConfiguredFeature.CODEC.xmap(
				Direct::new,
				direct -> direct.holder
		);

		public final Holder<ConfiguredFeature<?, ?>> holder;

		public Direct(Holder<ConfiguredFeature<?, ?>> holder) {
			super(Type.DIRECT);
			this.holder = holder;
		}

		@Override
		@Nullable
		public ConfiguredFeature<?, ?> select(RegistryAccess registries, RandomSource random) {
			return this.holder.value();
		}
	}

	public static class Tag extends ConfiguredFeatureProvider {
		public static final Codec<Tag> CODEC = TagCodec.of(Registries.CONFIGURED_FEATURE).xmap(
				Tag::new,
				tag -> tag.key
		);

		public final TagKey<ConfiguredFeature<?, ?>> key;

		public Tag(TagKey<ConfiguredFeature<?, ?>> key) {
			super(Type.TAG);
			this.key = key;
		}

		@Override
		@Nullable
		public ConfiguredFeature<?, ?> select(RegistryAccess registries, RandomSource random) {
			Registry<ConfiguredFeature<?, ?>> registry = registries.registryOrThrow(Registries.CONFIGURED_FEATURE);
			return registry.getTag(this.key)
					.flatMap(tag -> tag.getRandomElement(random))
					.map(Holder::value)
					.orElse(null);
		}
	}

	public static class Array extends ConfiguredFeatureProvider {
		public static final Codec<Array> CODEC = ConfiguredFeatureProvider.CODEC.listOf()
				.xmap(Array::new, array -> array.providers);

		public final List<ConfiguredFeatureProvider> providers;

		public Array(List<ConfiguredFeatureProvider> providers) {
			super(Type.ARRAY);
			this.providers = providers;
		}

		@Override
		@Nullable
		public ConfiguredFeature<?, ?> select(RegistryAccess registries, RandomSource random) {
			return Util.getRandomSafe(this.providers, random)
					.map(provider -> provider.select(registries, random))
					.orElse(null);
		}
	}
}
