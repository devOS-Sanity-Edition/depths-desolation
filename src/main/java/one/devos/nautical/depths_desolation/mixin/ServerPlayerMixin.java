package one.devos.nautical.depths_desolation.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.server.level.ServerPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
	@ModifyExpressionValue(
			method = "fudgeSpawnLocation",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/dimension/DimensionType;hasSkyLight()Z"
			)
	)
	private boolean dontFudge(boolean skylight) {
		return false;
	}
}
