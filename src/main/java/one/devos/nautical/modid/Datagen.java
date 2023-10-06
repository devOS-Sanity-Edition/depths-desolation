package one.devos.nautical.modid;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import one.devos.nautical.modid.util.DynamicRegistryBootstraps;
import one.devos.nautical.modid.feature.ModConfiguredFeatures;
import one.devos.nautical.modid.feature.ModPlacedFeatures;

public class Datagen implements DataGeneratorEntrypoint {
	public static final DynamicRegistryBootstraps BOOTSTRAPS = DynamicRegistryBootstraps.builder()
			.put(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
			.put(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
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
