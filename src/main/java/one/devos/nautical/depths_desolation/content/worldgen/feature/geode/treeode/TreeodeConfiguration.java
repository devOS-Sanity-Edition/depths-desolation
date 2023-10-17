package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class TreeodeConfiguration extends DecoratedGeodeConfiguration {
	public static final Codec<TreeodeConfiguration> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					GeodeConfiguration.CODEC.fieldOf("geode_config").forGetter(config -> config.geodeConfig),
					ConfiguredFeature.CODEC.fieldOf("tree_feature").forGetter(config -> config.treeFeature),
					Codec.list(BlockStateProvider.CODEC).fieldOf("floor_decor").forGetter(config -> config.floorDecor)
			).apply(instance, TreeodeConfiguration::new)
	);

	public final Holder<ConfiguredFeature<?, ?>> treeFeature;
	public final List<BlockStateProvider> floorDecor;

	public TreeodeConfiguration(GeodeConfiguration geodeConfig, Holder<ConfiguredFeature<?, ?>> treeFeature, List<BlockStateProvider> floorDecor) {
		super(geodeConfig);
		this.treeFeature = treeFeature;
		this.floorDecor = floorDecor;
	}
}
