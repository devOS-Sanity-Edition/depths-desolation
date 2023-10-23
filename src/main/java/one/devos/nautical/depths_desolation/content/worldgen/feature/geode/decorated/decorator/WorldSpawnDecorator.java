package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.decorator;

import com.mojang.serialization.Codec;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.DecoratedGeodeConfiguration;
import one.devos.nautical.depths_desolation.duck.ServerLevelExt;
import one.devos.nautical.depths_desolation.util.FloodFillPlane;

public class WorldSpawnDecorator extends GeodeDecorator {
	public static final Codec<WorldSpawnDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ExtraCodecs.POSITIVE_INT.fieldOf("search_radius").forGetter(decorator -> decorator.radius)
	).apply(instance, WorldSpawnDecorator::new));

	public final int radius;

	public WorldSpawnDecorator(int radius) {
		super(Type.WORLD_SPAWN);
		this.radius = radius;
	}

	@Override
	public void decorate(FeaturePlaceContext<DecoratedGeodeConfiguration> context, FloodFillPlane plane) {
		WorldGenLevel level = context.level();
		ServerLevel serverLevel = level.getLevel();
		if (!shouldSetSpawn(serverLevel))
			return; // spawn already set or was never needed
		for (BlockPos pos : plane.positions) {
			pos = moveToFloor(level, pos);
			if (isSolid(level.getBlockState(pos)))
				continue;
			if (isSolid(level.getBlockState(pos.above())))
				continue;

			// pos is a valid spawn
			setSpawnSet(serverLevel);
			DepthsAndDesolation.LOGGER.info("World spawn set to " + pos);
			serverLevel.setDefaultSpawnPos(pos, 0);
			break;
		}
	}

	public static boolean shouldSetSpawn(ServerLevel level) {
		return ((ServerLevelExt) level).dd$needsSpawnSet().get();
	}

	public static void setSpawnSet(ServerLevel level) {
		((ServerLevelExt) level).dd$needsSpawnSet().set(false);
	}
}
