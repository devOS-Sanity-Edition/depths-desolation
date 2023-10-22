package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import one.devos.nautical.depths_desolation.util.ConfiguredFeatureProvider;

public class Trees extends GeodeDecorator {
	public static final Codec<Trees> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ConfiguredFeatureProvider.CODEC.fieldOf("features").forGetter(trees -> trees.features),
			ExtraCodecs.POSITIVE_INT.fieldOf("max_placements").forGetter(trees -> trees.max)
	).apply(instance, Trees::new));

	public final ConfiguredFeatureProvider features;
	public final int max;

	public Trees(ConfiguredFeatureProvider features, int max) {
		super(Type.TREES);
		this.features = features;
		this.max = max;
	}

	@Override
	public void decorate(WorldGenLevel level, BlockPos inGeode) {

	}
}
