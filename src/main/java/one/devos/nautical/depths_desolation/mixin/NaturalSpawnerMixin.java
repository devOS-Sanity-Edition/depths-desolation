package one.devos.nautical.depths_desolation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {
	@Inject(
			method = "isSpawnPositionOk",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;"
			),
			cancellable = true
	)
	private static void filterOverworldSurfaceSpawns(SpawnPlacements.Type location, LevelReader world, BlockPos pos,
													 EntityType<?> entityType, CallbackInfoReturnable<Boolean> cir) {
		if (DepthsAndDesolation.isDesolate(world)) {
			if (!entityType.is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
				if (world.canSeeSky(pos)) {
					cir.setReturnValue(false);
				}
			}
		}
	}
}
