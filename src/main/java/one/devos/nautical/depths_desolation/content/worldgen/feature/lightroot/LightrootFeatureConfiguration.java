package one.devos.nautical.depths_desolation.content.worldgen.feature.lightroot;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class LightrootFeatureConfiguration implements FeatureConfiguration {
	public static final Codec<LightrootFeatureConfiguration> CODEC = Codec.unit(new LightrootFeatureConfiguration());
}
