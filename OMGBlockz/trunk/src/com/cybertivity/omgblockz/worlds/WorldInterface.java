package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.dimensions.*;
import com.cybertivity.omgblockz.utility.Coordinate3D;
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

    short[][][] getTerrain(Dimension dimension, Coordinate3D dimensionCoordinate, int blockCountX, int blockCountZ);
//    Chunk[][] getChunkArray(Dimension dimension, Coordinate3D dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ);

    DimensionInterface getDimension(Dimension dimension);
}
