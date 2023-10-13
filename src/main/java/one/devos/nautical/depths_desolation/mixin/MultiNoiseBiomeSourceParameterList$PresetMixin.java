package one.devos.nautical.depths_desolation.mixin;

import java.util.HashMap;
import java.util.Map;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import one.devos.nautical.depths_desolation.content.worldgen.parameters.DdParameters;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;

@Mixin(MultiNoiseBiomeSourceParameterList.Preset.class)
public class MultiNoiseBiomeSourceParameterList$PresetMixin {
	@ModifyExpressionValue(
			method = "<clinit>",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/stream/Stream;collect(Ljava/util/stream/Collector;)Ljava/lang/Object;"
			)
	)
	private static Object addDesolate(Object m) {
		if (m instanceof Map<?, ?> map) {
			Map<Object, Object> newMap = new HashMap<>(map);
			newMap.put(DepthsAndDesolation.id("desolate"), DdParameters.DESOLATE_PRESET);
			return newMap;
		} else {
			throw new IllegalArgumentException("Something has gone wrong! [" + m + "] is not a map!");
		}
	}
}
