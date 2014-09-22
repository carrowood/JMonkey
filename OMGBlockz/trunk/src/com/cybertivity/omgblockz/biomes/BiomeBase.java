package com.cybertivity.omgblockz.biomes;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate;
import java.util.ArrayList;

public abstract class BiomeBase implements BiomeInterface{

    protected float temperature = 0.8f;
    protected short biomeID = 1;
    protected String name = "Plains";
    protected ArrayList allowedAdjacentBiomeIDs = new ArrayList();
    public Biome biome = Biome.UNKNOWN;

    public ArrayList getAllowedAdjacentBiomeIDs() {
        return allowedAdjacentBiomeIDs;
    }
    public Chunk GetChunk(int seed, Coordinate coordinate) {
        //TODO  Chunk GetChunk(int seed, Coordinate coordinate)
        return new Chunk();
    }
    
}
