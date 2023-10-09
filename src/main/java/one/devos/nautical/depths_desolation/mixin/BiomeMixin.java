package one.devos.nautical.depths_desolation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

@Mixin(Biome.class)
public class BiomeMixin {
	@Redirect(
			method = "shouldSnow",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
			)
	)
	private boolean snowOverworld(Biome instance, BlockPos pos, LevelReader world, BlockPos posAgain) {
		if (world instanceof ServerLevel level && level.dimension() == Level.OVERWORLD) {
			return false;
		}
		return instance.warmEnoughToRain(pos);
	}

	@Redirect(
			method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"
			)
	)
	private boolean freezeOverworld(Biome instance, BlockPos pos, LevelReader world, BlockPos posAgain, boolean waterCheck) {
		if (world instanceof ServerLevel level && level.dimension() == Level.OVERWORLD) {
			return false;
		}
		return instance.warmEnoughToRain(pos);
	}
}
