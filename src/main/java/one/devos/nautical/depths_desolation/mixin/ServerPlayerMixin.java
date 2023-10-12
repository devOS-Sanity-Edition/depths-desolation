package one.devos.nautical.depths_desolation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import com.mojang.authlib.GameProfile;

import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
	public ServerPlayerMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}

	@Inject(method = "fudgeSpawnLocation", at = @At("HEAD"), cancellable = true)
	private void dontFudge(ServerLevel world, CallbackInfo ci) {
		if (DdWorldgen.isOverworld(world)) {
			BlockPos pos = world.getSharedSpawnPos();
			this.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
			ci.cancel();
		}
	}
}
