package one.devos.nautical.depths_desolation.content.worldgen.feature.geode.decorated;

import java.util.Locale;

public enum TreeodeType {
	OAK, BIRCH, SPRUCE, DARK_OAK, JUNGLE, ACACIA, MANGROVE, CHERRY;

	@Override
	public String toString() {
		return name().toLowerCase(Locale.ROOT);
	}
}
