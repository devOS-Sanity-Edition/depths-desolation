package one.devos.nautical.depths_desolation.mixin;

import java.util.List;

import com.llamalad7.mixinextras.sugar.Local;

import com.mojang.datafixers.util.Pair;

import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated.GeodePlaceContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.GeodeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

@Mixin(GeodeFeature.class)
public class GeodeFeatureMixin {
	@Inject(method = "place", at = @At(value = "TAIL", shift = Shift.BEFORE))
	private void decorate(FeaturePlaceContext<GeodeConfiguration> context, CallbackInfoReturnable<Boolean> cir,
						  @Local(ordinal = 0) List<Pair<BlockPos, Integer>> points) {
		if (context instanceof GeodePlaceContext ctx && !points.isEmpty()) {
			List<BlockPos> justPoints = points.stream().map(Pair::getFirst).toList();
			ctx.callback.accept(justPoints);
		}
	}
}
