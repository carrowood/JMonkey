package com.cybertivity.omgblockz.biomes;

import com.cybertivity.omgblockz.dimensions.DimensionInterface;
import com.cybertivity.omgblockz.utility.*;

public class BiomeManager {

    private static BiomeManager instance = null;

    private BiomeManager() {
    }

    static BiomeInterface DetermineBiome(int seed, Coordinate3D worldPosition, DimensionInterface dimension) {
        return new Plains();
    }

    public static BiomeManager getInstance() {
        if (instance == null) {
            instance = new BiomeManager();
        }
        return instance;
    }

    public BiomeInterface GetBiome(int seed, Coordinate3D dimensionCoordinate) {

        //first, return from cache if loaded; if not then
        //use 2D moise to get -1 to 1
        //determine biome by ranking the allowed biomes
        //TO DO
        return new Plains();
    }
}
