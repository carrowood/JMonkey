package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate;

public interface DimensionInterface {

    short getMaxLandheight();

    byte getSeaLevel();

    short getWorldHeight();

    public Chunk getChunk(int seed, Coordinate worldCoordinate);

    Chunk[][] getChunkArray(int seed, Coordinate dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ);

    Dimension getDimension();

}
