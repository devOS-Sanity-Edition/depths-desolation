package one.devos.nautical.depths_desolation.mixin;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@Mixin(Level.class)
public abstract class LevelMixin {
	@Shadow
	public abstract boolean isClientSide();

	@Inject(method = { "getRainLevel", "getThunderLevel" }, at = @At("HEAD"), cancellable = true)
	private void weatherInOverworld(float delta, CallbackInfoReturnable<Float> cir) {
		if (!this.isClientSide() && DepthsAndDesolation.isOverworld((Level) (Object) this)) {
			cir.setReturnValue(1f);
		}
	}

	@Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
	private void snowyOverworld(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (DepthsAndDesolation.isOverworld((Level) (Object) this)) {
			cir.setReturnValue(false);
		}
	}
}
