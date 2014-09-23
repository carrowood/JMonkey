package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate;

public interface DimensionInterface {

    short getMaxLandheight();

    short getSeaLevel();

    short getWorldHeight();

    public Chunk GetChunk(int seed, Coordinate worldCoordinate);

    Dimension getDimension();

}
