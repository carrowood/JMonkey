package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate3D;

public interface DimensionInterface {

    short getMaxLandheight();

    byte getSeaLevel();

    short getWorldHeight();

    public Chunk getChunk(int seed, Coordinate3D worldCoordinate);

    Chunk[][] getChunkArray(int seed, Coordinate3D dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ);

    Dimension getDimension();

}
