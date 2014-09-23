package com.cybertivity.omgblockz.biomes;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate;

public interface BiomeInterface {

    public Chunk GetChunk(int seed, Coordinate coordinate, short worldHieght, short maxLandLevel, short seaLevel);
}
