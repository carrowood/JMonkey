package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.biomes.BiomeInterface;
import com.cybertivity.omgblockz.biomes.*;
import com.cybertivity.omgblockz.utility.FileSystemHelper;
import java.util.ArrayList;

public class Overworld extends DimensionBase {

    private static final short WORLD_HEIGHT = 256;
    private static final short MAX_LAND_HEIGHT = 256;
    private static final byte SEA_LEVEL = 64;

    static {
        biomes.add(new Plains());
    }

    public Overworld(int seed, String path) {
        super(Dimension.OVERWORLD,seed, path + FileSystemHelper.GetFileSeparator() + "region", WORLD_HEIGHT, MAX_LAND_HEIGHT, SEA_LEVEL);

    }
}
