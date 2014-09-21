package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.dimensions.DimensionInterface;
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
}
