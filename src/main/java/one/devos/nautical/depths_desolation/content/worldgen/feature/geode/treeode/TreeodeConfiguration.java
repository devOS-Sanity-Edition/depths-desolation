package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import one.devos.nautical.depths_desolation.util.ConfiguredFeatureProvider;
import one.devos.nautical.depths_desolation.util.TagCodec;

import java.util.List;

public class TreeodeConfiguration extends DecoratedGeodeConfiguration {
	public static final Codec<TreeodeConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					GeodeConfiguration.CODEC.fieldOf("geode_config").forGetter(config -> config.geodeConfig),
					ConfiguredFeatureProvider.CODEC.fieldOf("trees").forGetter(config -> config.trees),
					Codec.list(BlockStateProvider.CODEC).fieldOf("floor_decor").forGetter(config -> config.floorDecor)
			).apply(instance, TreeodeConfiguration::new)
	);

	public final ConfiguredFeatureProvider trees;
	public final List<BlockStateProvider> floorDecor;

	public TreeodeConfiguration(GeodeConfiguration geodeConfig, ConfiguredFeatureProvider trees, List<BlockStateProvider> floorDecor) {
		super(geodeConfig);
		this.trees = trees;
		this.floorDecor = floorDecor;
	}
}
