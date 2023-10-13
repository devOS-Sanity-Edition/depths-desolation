package one.devos.nautical.depths_desolation;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdConfiguredFeatures;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdPlacedFeatures;
import one.devos.nautical.depths_desolation.content.worldgen.preset.DdPresets;
import one.devos.nautical.depths_desolation.content.worldgen.preset.parameters.DdParameters;
import one.devos.nautical.depths_desolation.util.DynamicRegistryBootstraps;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DdDatagen implements DataGeneratorEntrypoint {
	public static final DynamicRegistryBootstraps BOOTSTRAPS = DynamicRegistryBootstraps.builder()
			.put(Registries.CONFIGURED_FEATURE, DdConfiguredFeatures::bootstrap)
			.put(Registries.PLACED_FEATURE, DdPlacedFeatures::bootstrap)
			.put(Registries.WORLD_PRESET, DdPresets::bootstrap)
			.put(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, DdParameters::bootstrap)
			.build();

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		FabricDataGenerator.Pack pack = gen.createPack();
		pack.addProvider(BOOTSTRAPS::getDataProvider);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder builder) {
		BOOTSTRAPS.forEach(builder::add);
	}
}
