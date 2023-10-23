package one.devos.nautical.depths_desolation.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class FloodFillPlane {
	public final BlockPos center;
	public final ImmutableList<BlockPos> positions;

	public FloodFillPlane(LevelReader level, BlockPos center, Direction.Axis axis, int radius, SimpleTest test) {
		this(level, center, axis, radius, test.asTest());
	}

	public FloodFillPlane(LevelReader level, BlockPos center, Direction.Axis axis, int radius, Test test) {
		this.center = center;
		Builder<BlockPos> positions = ImmutableList.builder();

		double radiusSqr = radius * radius;
		Direction[] directions = getRelevantDirections(axis);
		Deque<BlockPos> checkNext = new ArrayDeque<>();
		checkNext.add(center);
		List<BlockPos> visited = new ArrayList<>();

		while (!checkNext.isEmpty()) {
			BlockPos pos = checkNext.removeFirst();
			visited.add(pos);
			if (center.distSqr(pos) > radiusSqr)
				continue; // too far
			BlockState state = level.getBlockState(pos);
			if (!test.isValid(state, pos))
				continue; // invalid

			// all valid
			positions.add(pos);

			// find neighbors to check next
			for (Direction direction : directions) {
				BlockPos next = pos.relative(direction);
				if (!visited.contains(next)) {
					checkNext.add(next);
				}
			}
		}

		this.positions = positions.build();
	}

	public static Direction[] getRelevantDirections(Direction.Axis axis) {
		Direction initial = switch (axis) {
			case Y, X -> Direction.SOUTH;
			case Z -> Direction.EAST;
		};

		return new Direction[] {
				initial,
				initial.getClockWise(axis),
				initial.getClockWise(axis).getClockWise(axis),
				initial.getClockWise(axis).getClockWise(axis).getClockWise(axis)
		};
	}

	public interface Test {
		boolean isValid(BlockState state, BlockPos pos);
	}

	public interface SimpleTest {
		boolean isValid(BlockState state);

		default Test asTest() {
			return (state, pos) -> this.isValid(state);
		}
	}
}
