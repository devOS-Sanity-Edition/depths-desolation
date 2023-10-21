package one.devos.nautical.depths_desolation.content.worldgen.preset;

import java.util.Map;

import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.dimensiontype.DdDimensionTypes;
import one.devos.nautical.depths_desolation.content.worldgen.parameters.DdParameters;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;

public class DdPresets {
	public static final ResourceKey<WorldPreset> DESOLATE = ResourceKey.create(Registries.WORLD_PRESET, DepthsAndDesolation.id("desolate"));

	public static void bootstrap(BootstapContext<WorldPreset> ctx) {
		// copied from WorldPresets
		HolderGetter<DimensionType> dimensionTypes = ctx.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<MultiNoiseBiomeSourceParameterList> parameterLists = ctx.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
		HolderGetter<NoiseGeneratorSettings> noiseSettings = ctx.lookup(Registries.NOISE_SETTINGS);
		HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);

		Holder<DimensionType> netherType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.NETHER);
		Holder<NoiseGeneratorSettings> netherNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER);
		Holder.Reference<MultiNoiseBiomeSourceParameterList> netherParameters = parameterLists.getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER);
		LevelStem nether = new LevelStem(netherType, new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(netherParameters), netherNoise));

		Holder<DimensionType> endType = dimensionTypes.getOrThrow(BuiltinDimensionTypes.END);
		Holder<NoiseGeneratorSettings> endNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.END);
		LevelStem end = new LevelStem(endType, new NoiseBasedChunkGenerator(TheEndBiomeSource.create(biomes), endNoise));

		// the important part

		Holder.Reference<MultiNoiseBiomeSourceParameterList> overworldParameters = parameterLists.getOrThrow(DdParameters.DESOLATE);
		MultiNoiseBiomeSource overworldBiomes = MultiNoiseBiomeSource.createFromPreset(overworldParameters);
		Reference<NoiseGeneratorSettings> overworldNoise = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);

		ctx.register(DESOLATE, new WorldPreset(Map.of(
				LevelStem.OVERWORLD, new LevelStem(
						dimensionTypes.getOrThrow(BuiltinDimensionTypes.OVERWORLD),
						new NoiseBasedChunkGenerator(overworldBiomes, overworldNoise)
				),
				LevelStem.NETHER, nether,
				LevelStem.END, end
		)));
	}
}
