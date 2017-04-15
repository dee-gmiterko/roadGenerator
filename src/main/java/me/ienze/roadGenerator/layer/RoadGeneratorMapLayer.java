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

		Vec start = new Vec(random.nextInt(getWidth()-1), random.nextInt(getHeight()-1));
		for(int k = 0; k < 2; k++) {
			Vec pointer = new Vec(start);
			
			while(isInRange(pointer)) {
				set(pointer.x, pointer.y, true);
				
				double bv = terrain.get(pointer.x, pointer.y);
				
				TreeMap<Double, Vec> possibleSides = new TreeMap<Double, Vec>();
				
				if(canPlace(pointer.x + 1, pointer.y, pointer)) {
					possibleSides.put(terrain.get(pointer.x + 1, pointer.y) - bv, new Vec(pointer.x + 1, pointer.y));
				}
				if(canPlace(pointer.x, pointer.y + 1, pointer)) {
					possibleSides.put(terrain.get(pointer.x, pointer.y + 1) - bv, new Vec(pointer.x, pointer.y + 1));
				}
				if(canPlace(pointer.x - 1, pointer.y, pointer)) {
					possibleSides.put(terrain.get(pointer.x - 1, pointer.y) - bv, new Vec(pointer.x - 1, pointer.y));
				}
				if(canPlace(pointer.x, pointer.y - 1, pointer)) {
					possibleSides.put(terrain.get(pointer.x, pointer.y - 1) - bv, new Vec(pointer.x, pointer.y - 1));
				}

				if(possibleSides.size() == 0) {
					break;
				}

				pointer = possibleSides.firstEntry().getValue();
			}
		}
	}

	private boolean canPlace(int x, int y, Vec ignore) {
		if(!isInRange(x, y) || get(x, y)) {
			return false;
		}
		if(isInRange(x + 1, y) && get(x + 1, y) && (ignore.x != x + 1 || ignore.y != y)) {
			return false;
		}
		if(isInRange(x, y + 1) && get(x, y + 1) && (ignore.x != x || ignore.y != y + 1)) {
			return false;
		}
		if(isInRange(x - 1, y) && get(x - 1, y) && (ignore.x != x - 1 || ignore.y != y)) {
			return false;
		}
		if(isInRange(x, y - 1) && get(x, y - 1) && (ignore.x != x || ignore.y != y - 1)) {
			return false;
		}
		return true;
	}

	private boolean isInRange(int x, int y) {
		return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
	}
	
	private boolean isInRange(Vec pointer) {
		return isInRange(pointer.x, pointer.y);
	}
}
