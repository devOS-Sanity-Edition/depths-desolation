package one.devos.nautical.depths_desolation.content.worldgen.feature.deepgen;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record DeepGenFeatureConfiguration(int minY, int maxY) implements FeatureConfiguration {
	public static final Codec<Integer> HEIGHT = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y);

	public static final Codec<DeepGenFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			HEIGHT.fieldOf("min_y").forGetter(DeepGenFeatureConfiguration::minY),
			HEIGHT.fieldOf("max_y").forGetter(DeepGenFeatureConfiguration::maxY)
	).apply(instance, DeepGenFeatureConfiguration::new));
}
