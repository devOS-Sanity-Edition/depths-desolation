package one.devos.nautical.depths_desolation.content;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class DdBlocks {
	public static final Block PERMAFROST = register("permafrost", new Block(
			QuiltBlockSettings.copy(Blocks.COARSE_DIRT).strength(0.7f)
	));
	public static final Block FULL_GRASS = register("full_grass", new Block(
			QuiltBlockSettings.copy(Blocks.GRASS_BLOCK).dropsLike(Blocks.GRASS_BLOCK)
	));

	private static Block register(String name, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, DepthsAndDesolation.id(name), block);
	}

	public static void init() {
	}
}
