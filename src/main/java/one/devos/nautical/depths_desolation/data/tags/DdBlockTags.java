package one.devos.nautical.depths_desolation.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import java.util.concurrent.CompletableFuture;

public class DdBlockTags extends FabricTagProvider.BlockTagProvider {
	public static final TagKey<Block> TREEODE_FLOOR_DECOR = create("treeode_floor_decor");

	public DdBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		getOrCreateTagBuilder(TREEODE_FLOOR_DECOR)
				.add( // most of small flowers, exclude special ones (torchflower, wither rose)
						Blocks.DANDELION, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET,
						Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP,
						Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY
				)
				.add( // most of tall flowers, exclude special (pitcher plant)
						Blocks.SUNFLOWER, Blocks.LILAC, Blocks.PEONY, Blocks.ROSE_BUSH
				) // bonuses
				.add(Blocks.MOSS_CARPET)
				.add(Blocks.AZALEA)
				.add(Blocks.FLOWERING_AZALEA);
	}

	public static TagKey<Block> create(String name) {
		return TagKey.create(Registries.BLOCK, DepthsAndDesolation.id(name));
	}
}
