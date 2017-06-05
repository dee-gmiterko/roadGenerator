package me.ienze.roadGenerator;

import me.ienze.roadGenerator.layer.RadialGradientMapLayer;
import me.ienze.roadGenerator.layer.RoadGeneratorMapLayer;
import me.ienze.roadGenerator.layer.TerrainMapLayer;
import me.ienze.twoDimMap.CombineMapLayer;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.io.GeneralMapImageWriter;

import java.io.File;
import java.io.IOException;

public class RoadGenerator {

    public RoadGenerator() {

        int seed = 421;

        /*
        Terrain example

        MapLayer<Float> terrainValley = new RadialGradientMapLayer(2048, 2048, 1024, 1024, 1024);
        MapLayer<Float> terrain0 = new TerrainMapLayer(2048, 2048, 0.0001f, seed+0);
        MapLayer<Float> terrain1 = new TerrainMapLayer(2048, 2048, 0.001f, seed+1);
        MapLayer<Float> terrain2 = new TerrainMapLayer(2048, 2048, 0.001f, seed+2);
        MapLayer<Float> terrain5 = new TerrainMapLayer(2048, 2048, 0.026f, seed+5);

        CombineMapLayer terrain = new CombineMapLayer(CombineMapLayer.CombineMode.AVG);
        terrain.addLayer(terrainValley);
        terrain.addLayer(terrainValley);
        terrain.addLayer(terrainValley);
        terrain.addLayer(terrain0);
        terrain.addLayer(terrain1);
        terrain.addLayer(terrain1);
        terrain.addLayer(terrain1);
        terrain.addLayer(terrain2);
        terrain.addLayer(terrain5);
        */

        MapLayer<Float> terrain = new TerrainMapLayer(256, 256, 0.01f, seed);
        MapLayer<Boolean> roads = new RoadGeneratorMapLayer(256, 256, terrain);

        CombineMapLayer output = new CombineMapLayer();
        output.addLayer(roads);
        output.addLayer(terrain);

        try {
            GeneralMapImageWriter writer = new GeneralMapImageWriter();
            writer.write(output, new File("out.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RoadGenerator();
    }

}
