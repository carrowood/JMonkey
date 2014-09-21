package com.cybertivity.omgblockz.Dimensions;

import com.cybertivity.omgblockz.Utility.FileSystemHelper;

public class Overworld extends DimensionBase {

    private static final int WORLD_HEIGHT = 256;
    private static final int MAX_LAND_HEIGHT = 256;
    private static final int SEA_LEVEL = 64;

    public Overworld(int seed, String path) {
        super(seed, path + FileSystemHelper.GetFileSeparator() + "region", WORLD_HEIGHT, MAX_LAND_HEIGHT, SEA_LEVEL);

    }
}
