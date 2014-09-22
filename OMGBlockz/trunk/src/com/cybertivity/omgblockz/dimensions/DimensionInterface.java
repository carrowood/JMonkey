package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.utility.Coordinate;

public interface DimensionInterface {

    int getMaxLandheight();

    int getSeaLevel();

    int getWorldHieght();

    public Chunk getChunk(Coordinate coordinate);

    Dimension getDimension();

}
