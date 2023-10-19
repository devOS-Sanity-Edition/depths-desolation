package one.devos.nautical.depths_desolation.content.worldgen.feature.lightroot;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class LightrootFeature extends Feature<LightrootFeatureConfiguration> {
	public LightrootFeature(Codec<LightrootFeatureConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<LightrootFeatureConfiguration> context) {
		setBlock(context.level(), context.origin(), Blocks.DIAMOND_BLOCK.defaultBlockState());
		return true;
	}
}
