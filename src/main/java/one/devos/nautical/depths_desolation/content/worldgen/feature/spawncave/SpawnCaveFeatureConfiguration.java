package one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public record SpawnCaveFeatureConfiguration(boolean bonusChest) implements FeatureConfiguration {
	public static final Codec<SpawnCaveFeatureConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.BOOL.fieldOf("bonus_chest").forGetter(SpawnCaveFeatureConfiguration::bonusChest)
			).apply(instance, SpawnCaveFeatureConfiguration::new)
	);
}
