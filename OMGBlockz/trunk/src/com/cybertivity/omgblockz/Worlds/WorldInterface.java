package com.cybertivity.omgblockz.Worlds;

import com.cybertivity.omgblockz.Dimensions.DimensionInterface;
import com.cybertivity.omgblockz.Utility.*;
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
