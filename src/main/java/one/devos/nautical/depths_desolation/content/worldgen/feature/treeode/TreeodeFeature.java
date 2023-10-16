package one.devos.nautical.depths_desolation.content.worldgen.feature.treeode;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

import java.util.Optional;

public class TreeodeFeature extends Feature<TreeodeConfiguration> {
	public TreeodeFeature(Codec<TreeodeConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<TreeodeConfiguration> context) {
		FeaturePlaceContext<GeodeConfiguration> geodeCtx = new FeaturePlaceContext<>(
				Optional.empty(), context.level(), context.chunkGenerator(),
				context.random(), context.origin(), context.config().geodeConfig()
		);
		boolean geodePlaced = Feature.GEODE.place(geodeCtx);
		if (!geodePlaced)
			return false;
		int radius = context.config().geodeConfig().outerWallDistance.getMaxValue();
		System.out.println(radius);
		return true;
	}
}
