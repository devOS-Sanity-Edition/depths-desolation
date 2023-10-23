package one.devos.nautical.depths_desolation;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.ResetChunksCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;

import com.mojang.brigadier.arguments.ArgumentType;

import com.mojang.brigadier.arguments.IntegerArgumentType;

import one.devos.nautical.depths_desolation.content.DdBlocks;

import one.devos.nautical.depths_desolation.content.DdTabs;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

import one.devos.nautical.depths_desolation.content.DdItems;
import one.devos.nautical.depths_desolation.content.DdWorldgen;

import one.devos.nautical.depths_desolation.util.FloodFillPlane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepthsAndDesolation implements ModInitializer {
	public static final String ID = "depths_desolation";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize(ModContainer mod) {
		DdBlocks.init();
		DdItems.init();
		DdTabs.init();
		DdWorldgen.init();

		CommandRegistrationCallback.EVENT.register((dispatcher, __, env) -> {
			ResetChunksCommand.register(dispatcher);
			dispatcher.register(literal("floodfillplane").then(
					argument("radius", IntegerArgumentType.integer(0))
							.then(literal("X").executes(ctx -> {
								int r = IntegerArgumentType.getInteger(ctx, "radius");
								ServerLevel level = ctx.getSource().getLevel();
								BlockPos pos = BlockPos.containing(ctx.getSource().getPosition());
								FloodFillPlane plane = new FloodFillPlane(level, pos, Axis.X, r, BlockStateBase::isAir);
								plane.positions.forEach(p -> level.setBlockAndUpdate(p, Blocks.DIAMOND_BLOCK.defaultBlockState()));
								level.setBlockAndUpdate(plane.center, Blocks.GOLD_BLOCK.defaultBlockState());
								return 1;
							}))
							.then(literal("Y").executes(ctx -> {
								int r = IntegerArgumentType.getInteger(ctx, "radius");
								ServerLevel level = ctx.getSource().getLevel();
								BlockPos pos = BlockPos.containing(ctx.getSource().getPosition());
								FloodFillPlane plane = new FloodFillPlane(level, pos, Axis.Y, r, BlockStateBase::isAir);
								plane.positions.forEach(p -> level.setBlockAndUpdate(p, Blocks.DIAMOND_BLOCK.defaultBlockState()));
								level.setBlockAndUpdate(plane.center, Blocks.GOLD_BLOCK.defaultBlockState());
								return 1;
							}))
							.then(literal("Z").executes(ctx -> {
								int r = IntegerArgumentType.getInteger(ctx, "radius");
								ServerLevel level = ctx.getSource().getLevel();
								BlockPos pos = BlockPos.containing(ctx.getSource().getPosition());
								FloodFillPlane plane = new FloodFillPlane(level, pos, Axis.Z, r, BlockStateBase::isAir);
								plane.positions.forEach(p -> level.setBlockAndUpdate(p, Blocks.DIAMOND_BLOCK.defaultBlockState()));
								level.setBlockAndUpdate(plane.center, Blocks.GOLD_BLOCK.defaultBlockState());
								return 1;
							}))
					)
			);
		});
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
