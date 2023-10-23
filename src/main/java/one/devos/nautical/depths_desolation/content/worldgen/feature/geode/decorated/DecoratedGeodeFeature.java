package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import com.mojang.serialization.Codec;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator.GeodeDecorator;
import one.devos.nautical.depths_desolation.util.FloodFillPlane;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class DecoratedGeodeFeature extends Feature<DecoratedGeodeConfiguration> {
	public DecoratedGeodeFeature(Codec<DecoratedGeodeConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<DecoratedGeodeConfiguration> context) {
		DecoratedGeodeConfiguration config = context.config();
		GeodePlaceContext geodeCtx = new GeodePlaceContext(context, config.geodeConfig(), points -> {
			BlockPos pos = points.get(0); // guaranteed non-empty
			FloodFillPlane plane = new FloodFillPlane(context.level(), pos, Axis.Y, 5, state -> state.canBeReplaced());
			for (GeodeDecorator decorator : config.decorators()) {
				decorator.decorate(context, plane);
			}
		});

		return Feature.GEODE.place(geodeCtx);
	}
}
