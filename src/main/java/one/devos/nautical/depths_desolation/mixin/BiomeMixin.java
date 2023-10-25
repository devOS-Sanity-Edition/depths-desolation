package one.devos.nautical.depths_desolation.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

@Mixin(Biome.class)
public class BiomeMixin {
	@ModifyExpressionValue(
			method = "shouldSnow",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
			)
	)
	private boolean snowOverworld(boolean warmEnoughToRain, LevelReader world, BlockPos pos) {
		return !DepthsAndDesolation.isDesolate(world) && warmEnoughToRain;
	}

	@ModifyExpressionValue(
			method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
			)
	)
	private boolean freezeOverworld(boolean warmEnoughToRain, LevelReader world, BlockPos pos, boolean waterCheck) {
		return !DepthsAndDesolation.isDesolate(world) && warmEnoughToRain;
	}
}
