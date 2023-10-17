package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public class GeodePlaceContext extends FeaturePlaceContext<GeodeConfiguration> {
	public final DistPointConsumer callback;

	public GeodePlaceContext(FeaturePlaceContext<?> ctx, GeodeConfiguration config, DistPointConsumer callback) {
		super(ctx.topFeature(), ctx.level(), ctx.chunkGenerator(), ctx.random(), ctx.origin(), config);
		this.callback = callback;
	}
}
