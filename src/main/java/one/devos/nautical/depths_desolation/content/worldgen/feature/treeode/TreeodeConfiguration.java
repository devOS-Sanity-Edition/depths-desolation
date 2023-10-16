package one.devos.nautical.depths_desolation.content.worldgen.feature.treeode;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public record TreeodeConfiguration(GeodeConfiguration geodeConfig, Holder<ConfiguredFeature<?, ?>> treeFeature) implements FeatureConfiguration {
	public static final Codec<TreeodeConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					GeodeConfiguration.CODEC.fieldOf("geode_config").forGetter(TreeodeConfiguration::geodeConfig),
					ConfiguredFeature.CODEC.fieldOf("tree_feature").forGetter(TreeodeConfiguration::treeFeature)
			).apply(instance, TreeodeConfiguration::new)
	);
}
