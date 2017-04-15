package me.ienze.roadGenerator.layer;

import me.ienze.twoDimMap.BooleanMapLayer;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.Vec;

import java.util.Random;
import java.util.TreeMap;

public class RoadGeneratorMapLayer extends BooleanMapLayer {

	private final MapLayer<Float> terrain;

	public RoadGeneratorMapLayer(int width, int height, MapLayer<Float> terrain) {
		this(new Vec(width, height), terrain);
	}

	public RoadGeneratorMapLayer(int width, int height, MapLayer<Float> terrain, Random random) {
		this(new Vec(width, height), terrain, random);
	}

	public RoadGeneratorMapLayer(Vec size, MapLayer<Float> terrain) {
		this(size, terrain, new Random());
	}

	public RoadGeneratorMapLayer(Vec size, MapLayer<Float> terrain, Random random) {
		super(size);
		this.terrain = terrain;

		int desiredRoadCount = 400;

		Vec pointer = new Vec(random.nextInt(getWidth()-1), random.nextInt(getHeight()-1));
		int roadCount = 0;

		while(roadCount < desiredRoadCount) {

			boolean firstTile = true;

			while (isInRange(pointer)) {

				if (get(pointer.x, pointer.y) && !firstTile) {
					break;
				}

				set(pointer.x, pointer.y, true);
				roadCount++;

				double bv = terrain.get(pointer.x, pointer.y);

				TreeMap<Double, Vec> possibleSides = new TreeMap<Double, Vec>();

				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx == 0 && dy == 0) {
							continue;
						}

						double cost = heightCost(terrain.get(pointer.x + dx, pointer.y + dy), bv);

						cost += roadCost(pointer.x + dx, pointer.y + dy, 16) * 0.52;

						double rx = (pointer.x + dx) - getWidth() / 2;
						double ry = (pointer.y + dy) - getHeight() / 2;
						cost -= 1 / Math.sqrt(rx*rx+ry*ry);

						if(cost < 0.25) {
							possibleSides.put(cost, new Vec(pointer.x + dx, pointer.y + dy));
						}
					}
				}

				if (possibleSides.size() == 0) {
					break;
				}

				pointer = possibleSides.firstEntry().getValue();
				firstTile = false;
			}

			do {
				pointer = new Vec(random.nextInt(getWidth() - 1), random.nextInt(getHeight() - 1));
			} while (!get(pointer.getX(), pointer.getY()));
		}
	}

	private double heightCost(double height, double bv) {
		return Math.abs(height - bv);
	}

	private double roadCost(int x, int y, int distance) {
		double cost = 0;
		for(int dx=-distance; dx<=distance; dx++) {
			for(int dy=-distance; dy<=distance; dy++) {
				if(isInRange(x+dx, y+dy) && get(x+dx, y+dy)) {
					cost += 1 / Math.sqrt(dx*dx+dy*dy);
				}
			}
		}
		return cost / distance;
	}

	private boolean isInRange(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}
	
	private boolean isInRange(Vec pointer) {
		return isInRange(pointer.x, pointer.y);
	}
}
