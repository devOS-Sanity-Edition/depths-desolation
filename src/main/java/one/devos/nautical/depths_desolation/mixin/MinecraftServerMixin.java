package one.devos.nautical.depths_desolation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import one.devos.nautical.depths_desolation.duck.ServerLevelExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(method = "setInitialSpawn", at = @At("HEAD"), cancellable = true)
	private static void handleTreeodeSpawn(ServerLevel world, ServerLevelData worldProperties,
										   boolean bonusChest, boolean debugWorld, CallbackInfo ci) {
		if (debugWorld)
			return;
		AtomicBoolean status = ((ServerLevelExt) world).dd$needsSpawnSet();
		status.set(true);
		worldProperties.setSpawn(BlockPos.ZERO, 0);
		ci.cancel();
	}
}
