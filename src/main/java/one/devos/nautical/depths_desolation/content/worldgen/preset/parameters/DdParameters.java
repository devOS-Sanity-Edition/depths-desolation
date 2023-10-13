package one.devos.nautical.depths_desolation.content.worldgen.preset.parameters;

import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import one.devos.nautical.depths_desolation.content.worldgen.preset.DesolateOverworldBiomeBuilder;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList.Preset;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList.Preset.SourceProvider;

public class DdParameters {
	public static final ResourceKey<MultiNoiseBiomeSourceParameterList> DESOLATE = ResourceKey.create(
			Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, DepthsAndDesolation.id("desolate")
	);

	// added to map in mixin
	public static final Preset DESOLATE_PRESET = new Preset(DepthsAndDesolation.id("desolate"), new SourceProvider() {
		@Override
		@NotNull
		public <T> ParameterList<T> apply(Function<ResourceKey<Biome>, T> function) {
			Builder<Pair<ParameterPoint, T>> builder = ImmutableList.builder();
			new DesolateOverworldBiomeBuilder().addBiomes(pair -> builder.add(pair.mapSecond(function)));
			return new Climate.ParameterList<>(builder.build());
		}
	});

	public static void bootstrap(BootstapContext<MultiNoiseBiomeSourceParameterList> ctx) {
		HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
		ctx.register(DESOLATE, new MultiNoiseBiomeSourceParameterList(DESOLATE_PRESET, biomes));
	}
}
