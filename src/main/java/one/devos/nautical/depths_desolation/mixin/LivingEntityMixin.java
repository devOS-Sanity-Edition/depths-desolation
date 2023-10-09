package one.devos.nautical.depths_desolation.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> variant, Level world) {
		super(variant, world);
	}

	@ModifyExpressionValue(
			method = "aiStep",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/entity/LivingEntity;isInPowderSnow:Z"
			)
	)
	private boolean overworldSurfaceFreezes(boolean inPowderSnow) {
		if (inPowderSnow) {
			return true;
		}

		Level level = this.level();
		if (DepthsAndDesolation.isOverworld(level)) {
			return level.canSeeSkyFromBelowWater(this.blockPosition());
		}

		return false;
	}
}
