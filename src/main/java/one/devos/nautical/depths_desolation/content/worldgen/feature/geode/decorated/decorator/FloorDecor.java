package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class FloorDecor extends GeodeDecorator {
	public static final Codec<FloorDecor> CODEC = null;

	public FloorDecor() {
		super(Type.FLOOR_DECOR);
	}

	@Override
	public void decorate(WorldGenLevel level, BlockPos inGeode) {

	}
}
