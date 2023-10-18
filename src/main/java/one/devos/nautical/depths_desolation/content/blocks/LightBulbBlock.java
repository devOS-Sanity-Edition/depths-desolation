package one.devos.nautical.depths_desolation.content.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LightBulbBlock extends DirectionalBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	// bulbs placed in world gen will not grow
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

	public static final int MAX_AGE = 3;
	public static final float GROW_CHANCE = 1 / 5f;
	public static final int[] LIGHT_VALUES = {0, 5, 10, 15};

	public LightBulbBlock(Properties properties) {
		super(properties);
		registerDefaultState(
				stateDefinition.any()
						.setValue(FACING, Direction.NORTH)
						.setValue(AGE, 0)
						.setValue(NATURAL, false)
		);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING, AGE, NATURAL);
	}

	@Override
	@NotNull
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return super.getShape(state, world, pos, context);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Direction clickedFace = ctx.getClickedFace();
		return this.defaultBlockState().setValue(FACING, clickedFace.getOpposite());
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return !state.getValue(NATURAL) && state.getValue(AGE) < MAX_AGE;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (random.nextFloat() > GROW_CHANCE) {
			int age = state.getValue(AGE);
			BlockState newState = state.setValue(AGE, age + 1);
			world.setBlockAndUpdate(pos, newState);
		}
	}

	@Override
	@NotNull
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return state.canSurvive(world, pos) ? state : Blocks.AIR.defaultBlockState();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		Direction facing = state.getValue(FACING);
		BlockPos adjacent = pos.relative(facing);
		BlockState wallState = world.getBlockState(adjacent);
		return wallState.isFaceSturdy(world, adjacent, facing.getOpposite(), SupportType.FULL);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
		return state.getValue(AGE) < MAX_AGE;
	}

	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		int newAge = state.getValue(AGE) + 1;
		BlockState newState = state.setValue(AGE, newAge).setValue(NATURAL, true);
		world.setBlockAndUpdate(pos, newState);
	}

	public static int getLight(BlockState state) {
		int age = state.getValue(AGE);
		return LIGHT_VALUES[age];
	}
}
