package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.utility.FileSystemHelper;

public class Nether extends DimensionBase {

    private static final short WORLD_HEIGHT = 256;
    private static final short MAX_LAND_HEIGHT = 256;
    private static final short SEA_LEVEL = -1;
    
    public Nether(int seed, String path)  {
        super(Dimension.NETHER, seed,
                path + FileSystemHelper.GetFileSeparator() + "DIM-1" + FileSystemHelper.GetFileSeparator() + "region",
                WORLD_HEIGHT, MAX_LAND_HEIGHT, SEA_LEVEL);
    }
}
