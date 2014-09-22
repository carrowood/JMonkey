package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.dimensions.Dimension;
import com.cybertivity.omgblockz.dimensions.DimensionInterface;
import com.cybertivity.omgblockz.utility.Coordinate;
import java.util.ArrayList;

public interface WorldInterface {

    void Save();

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
    void AddDimension(DimensionInterface dimension);

    Chunk getChunk(Dimension dimension, Coordinate coordinate);
}
