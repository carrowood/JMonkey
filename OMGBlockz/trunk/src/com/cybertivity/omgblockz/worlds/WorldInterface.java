package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.dimensions.*;
import com.cybertivity.omgblockz.utility.Coordinate;
import java.util.ArrayList;

public interface WorldInterface {

    void save();

    String getName();

    String getPath();

    int getSeed();

    /**
     * @return the dimensions
     */
    ArrayList<DimensionInterface> getDimensions();

    /**
     * @param dimensions the dimensions to set
     */
    void addDimension(DimensionInterface dimension);

    Chunk getChunk(Dimension dimension, Coordinate coordinate);

    Chunk[][] getChunkArray(Dimension dimension, Coordinate dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ);
}
