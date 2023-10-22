package one.devos.nautical.depths_desolation.util;

import com.google.common.collect.Iterators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.world.level.LevelReader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FloodFillPlane implements Iterator<BlockPos> {
	private static final Direction[] directions = Direction.values();

	private final LevelReader level;
	private final BlockPos center;
	private final Direction.Axis axis;
	private final int radius;

	private final List<BlockPos> frontier = new ArrayList<>();

	public FloodFillPlane(LevelReader level, BlockPos center, Direction.Axis axis, int radius) {
		this.level = level;
		this.center = center;
		this.axis = axis;
		this.radius = radius;

		Direction initial = switch (axis) {
			case Y -> Direction.DOWN;
			case X -> Direction.EAST;
			case Z -> Direction.SOUTH;
		};

	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public BlockPos next() {
		return null;
	}

	public static Iterable<BlockPos> create(LevelReader level, BlockPos center, Direction.Axis axis, int radius) {
		return () -> new FloodFillPlane(level, center, axis, radius);
	}

	public static Iterable<Direction> fullRotation(Direction direction, Direction.Axis axis) {
		return () -> Iterators.forArray(
				direction,
				direction.getClockWise(axis),
				direction.getClockWise(axis).getClockWise(axis),
				direction.getClockWise(axis).getClockWise(axis).getClockWise(axis)
		);
	}
}
