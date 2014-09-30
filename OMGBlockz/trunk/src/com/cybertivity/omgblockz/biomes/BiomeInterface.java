package com.cybertivity.omgblockz.biomes;

import com.cybertivity.omgblockz.dimensions.DimensionBase;
import com.cybertivity.omgblockz.utility.Coordinate3D;

public interface BiomeInterface {

    public short[][][] getTerrain(short[][][] blockIds, int seed, int chunkCoordinateX, int chunkCoordinateZ, DimensionBase dimension);

    //Chunk[][] getChunkArray(int seed, int chunkCoordinateX, int chunkCoordinateZ, int arrayBoundsX, int arrayBoundsZ, DimensionBase dimension);
}
