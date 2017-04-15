package me.ienze.roadGenerator;

import me.ienze.roadGenerator.layer.RoadGeneratorMapLayer;
import me.ienze.roadGenerator.layer.TerrainMapLayer;
import me.ienze.twoDimMap.CombineMapLayer;
import me.ienze.twoDimMap.MapLayer;
import me.ienze.twoDimMap.io.GeneralMapImageWriter;

import java.io.File;
import java.io.IOException;

public class RoadGenerator {

    public RoadGenerator() {

        MapLayer<Float> terrain = new TerrainMapLayer(0.04f);
        MapLayer<Boolean> roads = new RoadGeneratorMapLayer(64, 64, terrain);

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
