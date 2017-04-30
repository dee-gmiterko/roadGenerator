package me.ienze.roadGenerator.layer;

import com.flowpowered.noise.model.Plane;
import com.flowpowered.noise.module.modifier.Abs;
import com.flowpowered.noise.module.modifier.Clamp;
import com.flowpowered.noise.module.source.Perlin;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.Vec;

import java.awt.*;
import java.util.Random;

public class RadialGradientMapLayer implements MapLayer<Float> {

    private Vec size;
    private Vec center;
    private float distanceSquared;

    public RadialGradientMapLayer(int width, int height, int centerX, int centerY, float distance) {
        this(new Vec(width, height), new Vec(centerX, centerY), distance);
    }

    public RadialGradientMapLayer(Vec size, Vec center, float distance) {
        this.size = size;
        this.center = center;
        this.distanceSquared = distance * distance;
    }

    @Override
    public Vec getSize() {
        return size;
    }

    @Override
    public int getWidth() {
        return size.x;
    }

    @Override
    public int getHeight() {
        return size.y;
    }

    @Override
    public Float get(int x, int y) {
        float dx = center.x - x;
        float dy = center.y - y;
        return Math.max(0.0f, Math.min(1.0f, (dx * dx + dy * dy) / distanceSquared));
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
