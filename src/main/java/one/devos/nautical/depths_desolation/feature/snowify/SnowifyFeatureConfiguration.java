package one.devos.nautical.depths_desolation.feature.snowify;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SnowifyFeatureConfiguration(int depth) implements FeatureConfiguration {
	public static final Codec<SnowifyFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.intRange(0, Integer.MAX_VALUE).fieldOf("depth").forGetter(SnowifyFeatureConfiguration::depth)
			).apply(instance, SnowifyFeatureConfiguration::new)
	);
}
