package one.devos.nautical.depths_desolation.mixin;

import one.devos.nautical.depths_desolation.content.DdWorldgen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor {
	@Shadow
	public abstract boolean isClientSide();

	@Inject(method = "getRainLevel", at = @At("HEAD"), cancellable = true)
	private void alwaysSnowyOverworld(float delta, CallbackInfoReturnable<Float> cir) {
		if (!this.isClientSide() && DdWorldgen.isOverworld(this)) {
			cir.setReturnValue(1f);
		}
	}

	@Inject(method = "getThunderLevel", at = @At("HEAD"), cancellable = true)
	private void neverThunderOverworld(float delta, CallbackInfoReturnable<Float> cir) {
		if (!this.isClientSide() && DdWorldgen.isOverworld(this)) {
			cir.setReturnValue(0f);
		}
	}

	@Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
	private void snowyOverworld(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (DdWorldgen.isOverworld(this)) {
			cir.setReturnValue(false);
		}
	}
}
