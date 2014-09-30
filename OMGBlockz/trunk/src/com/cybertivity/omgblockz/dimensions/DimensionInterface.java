package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.utility.Coordinate3D;

public interface DimensionInterface {

    short getMaxLandheight();

    byte getSeaLevel();

    short getWorldHeight();

    public short[][][] getTerrain(int seed, Coordinate3D dimensionCoordinate, int blockCountX, int blockCountZ);

//    Chunk[][] getChunkArray(int seed, Coordinate3D dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ);

    Dimension getDimension();

}
