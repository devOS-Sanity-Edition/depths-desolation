package one.devos.nautical.depths_desolation.content.worldgen.feature.spawncave;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import one.devos.nautical.depths_desolation.DepthsAndDesolation;
import one.devos.nautical.depths_desolation.content.worldgen.feature.DdConfiguredFeatures;

public class SpawnCaveFeature extends Feature<SpawnCaveFeatureConfiguration> {
	public static final ResourceLocation STRUCTURE_ID = DepthsAndDesolation.id("spawn_cave");
	public static final ResourceLocation STRUCTURE_BONUS_ID = DepthsAndDesolation.id("spawn_cave_bonus");

	public SpawnCaveFeature(Codec<SpawnCaveFeatureConfiguration> configCodec) {
		super(configCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SpawnCaveFeatureConfiguration> context) {
		BlockPos spawnPos = context.origin();
		WorldGenLevel level = context.level();

		boolean bonusChest = context.config().bonusChest();
		ResourceLocation structureId = bonusChest ? STRUCTURE_BONUS_ID : STRUCTURE_ID;

		level.getLevel().getStructureManager().get(structureId).ifPresentOrElse(structure -> {
			Vec3i bounds = structure.getSize();
			BlockPos corner = new BlockPos(
					spawnPos.getX() - (bounds.getX() / 2),
					spawnPos.getY() - (bounds.getY() / 2),
					spawnPos.getZ() - (bounds.getZ() / 2)
			);
			structure.placeInWorld(
					level,
					corner,
					corner,
					new StructurePlaceSettings(),
					context.random(),
					Block.UPDATE_ALL
			);
		}, () -> {
			level.setBlock(spawnPos.below(), Blocks.STONE.defaultBlockState(), Block.UPDATE_CLIENTS);
			level.setBlock(spawnPos, Blocks.OAK_SIGN.defaultBlockState(), Block.UPDATE_CLIENTS);
			BlockEntity be = level.getBlockEntity(spawnPos);
			if (be instanceof SignBlockEntity sign) {
				sign.updateText(
						text -> text.setMessage(0, Component.literal("Failed to"))
								.setMessage(1, Component.literal("find structure"))
								.setMessage(2, Component.literal(":("))
								.setMessage(3, Component.literal("bonus: " + bonusChest)),
						true
				);
			}
		});
		return true;
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> get(boolean bonusChest) {
		return bonusChest ? DdConfiguredFeatures.SPAWN_CAVE_BONUS : DdConfiguredFeatures.SPAWN_CAVE;
	}
}
