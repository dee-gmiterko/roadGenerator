package me.ienze.roadGenerator.layer;

import com.flowpowered.noise.model.Plane;
import com.flowpowered.noise.module.combiner.Max;
import com.flowpowered.noise.module.combiner.Select;
import com.flowpowered.noise.module.modifier.Abs;
import com.flowpowered.noise.module.modifier.Clamp;
import com.flowpowered.noise.module.source.Perlin;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.Vec;

import java.awt.Color;
import java.util.Random;

public class TerrainMapLayer implements MapLayer<Float> {

    private final Plane perlinPlane;

    public TerrainMapLayer(float frequency) {
        this(frequency, new Random());
    }

    public TerrainMapLayer(float frequency, Random random) {
        this(frequency, random.nextInt());
    }

    public TerrainMapLayer(float frequency, int seed) {

        Perlin perlin = new Perlin();
        perlin.setFrequency(frequency);
        perlin.setSeed(seed);

        Abs abs = new Abs();
        abs.setSourceModule(0, perlin);

        Clamp clamp = new Clamp();
        clamp.setLowerBound(0.0);
        clamp.setUpperBound(1.0);
        clamp.setSourceModule(0, abs);

        this.perlinPlane = new Plane(clamp);
    }

    @Override
    public Vec getSize() {
        return new Vec(0, 0);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Float get(int x, int y) {
        return (float) perlinPlane.getValue(x, y);
    }

    @Override
    public void set(int x, int y, Float value) {
        throw new UnsupportedOperationException("Perlin noise layer cannot by modified");
    }

    @Override
    public Color getColor(int x, int y) {
        float value = get(x, y);
        return new Color(value, value, value);
    }

    @Override
    public void setColor(int x, int y, Color color) {
        throw new UnsupportedOperationException("Perlin noise layer cannot by modified");
    }
}
