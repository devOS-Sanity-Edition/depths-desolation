package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public class DecoratedGeodeConfiguration implements FeatureConfiguration {
	public final GeodeConfiguration geodeConfig;

	public DecoratedGeodeConfiguration(GeodeConfiguration geodeConfig) {
		this.geodeConfig = geodeConfig;
	}
}
