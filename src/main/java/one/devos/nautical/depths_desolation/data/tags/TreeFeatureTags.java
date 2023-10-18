package one.devos.nautical.depths_desolation.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.geode.treeode.TreeodeType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TreeFeatureTags extends FabricTagProvider<ConfiguredFeature<?, ?>> {
	public static final TagKey<ConfiguredFeature<?, ?>> OAK_TREES = tag("oak_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> BIRCH_TREES = tag("birch_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> SPRUCE_TREES = tag("spruce_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> DARK_OAK_TREES = tag("dark_oak_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> JUNGLE_TREES = tag("jungle_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> ACACIA_TREES = tag("acacia_trees");
	public static final TagKey<ConfiguredFeature<?, ?>> CHERRY_TREES = tag("cherry_trees");

	public static final Map<TreeodeType, TagKey<ConfiguredFeature<?, ?>>> BY_TYPE = Map.of(
			TreeodeType.OAK, OAK_TREES,
			TreeodeType.BIRCH, BIRCH_TREES,
			TreeodeType.SPRUCE, SPRUCE_TREES,
			TreeodeType.DARK_OAK, DARK_OAK_TREES,
			TreeodeType.JUNGLE, JUNGLE_TREES,
			TreeodeType.ACACIA, ACACIA_TREES,
			TreeodeType.CHERRY, CHERRY_TREES
	);

	public TreeFeatureTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.CONFIGURED_FEATURE, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		tag(OAK_TREES).add(TreeFeatures.OAK, TreeFeatures.FANCY_OAK);
		tag(BIRCH_TREES).add(TreeFeatures.BIRCH);
		tag(SPRUCE_TREES).add(TreeFeatures.SPRUCE);
		tag(DARK_OAK_TREES).add(TreeFeatures.DARK_OAK);
		tag(JUNGLE_TREES).add(TreeFeatures.JUNGLE_TREE);
		tag(ACACIA_TREES).add(TreeFeatures.ACACIA);
		tag(CHERRY_TREES).add(TreeFeatures.ACACIA);
	}

	private static TagKey<ConfiguredFeature<?, ?>> tag(String name) {
		return TagKey.create(Registries.CONFIGURED_FEATURE, DepthsAndDesolation.id(name));
	}
}
