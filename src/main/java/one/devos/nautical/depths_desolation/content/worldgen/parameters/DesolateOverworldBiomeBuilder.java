package one.devos.nautical.depths_desolation.content.worldgen.parameters;

import java.util.Map;
import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;

public class DesolateOverworldBiomeBuilder extends OverworldBiomeBuilder {
	public static final Parameter FULL_RANGE = Parameter.span(-1, 1);

	public static final Map<ResourceKey<Biome>, ParameterPoint> OVERRIDES = Map.of(
			Biomes.LUSH_CAVES, Climate.parameters(
					FULL_RANGE,
					Parameter.span(0.4f, 1), // original: 0.7 -> 1
					FULL_RANGE,
					FULL_RANGE,
					FULL_RANGE,
					FULL_RANGE,
					0
			)
	);

	@Override
	public void addBiomes(Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> output) {
		super.addBiomes(pair -> {
			ResourceKey<Biome> biome = pair.getSecond();
			if (OVERRIDES.containsKey(biome)) {
				pair = new Pair<>(OVERRIDES.get(biome), biome);
			}
			output.accept(pair);
		});
	}
}
