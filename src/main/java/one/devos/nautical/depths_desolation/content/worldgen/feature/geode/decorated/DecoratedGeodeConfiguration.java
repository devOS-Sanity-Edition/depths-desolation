package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import java.util.List;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator.GeodeDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public record DecoratedGeodeConfiguration(GeodeConfiguration geodeConfig, List<GeodeDecorator> decorators) implements FeatureConfiguration {
	public static Codec<DecoratedGeodeConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			GeodeConfiguration.CODEC.fieldOf("geode_config").forGetter(DecoratedGeodeConfiguration::geodeConfig),
			GeodeDecorator.CODEC.listOf().fieldOf("decorators").forGetter(DecoratedGeodeConfiguration::decorators)
	).apply(instance, DecoratedGeodeConfiguration::new));
}
