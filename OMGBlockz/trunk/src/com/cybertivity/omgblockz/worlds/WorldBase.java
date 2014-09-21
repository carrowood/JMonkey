package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.utility.FileSystemHelper;
import com.cybertivity.omgblockz.dimensions.DimensionInterface;
import java.util.ArrayList;

public abstract class WorldBase implements WorldInterface {

    private int seed;
    private String name;
    private ArrayList<DimensionInterface> dimensions = new ArrayList<DimensionInterface>();
    private String path;

    public static WorldBase LoadWorld(String path, String name) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    private WorldBase() {
    }

    public WorldBase(String seed, String path, String name) {
        this.seed = GenerateSeed(seed);
        this.name = name;
        this.path = path + FileSystemHelper.GetFileSeparator() + name;
        FileSystemHelper.createDirectoryIfNeeded(this.path);
    }

    private int GenerateSeed(String seed) {
        if (seed == null) {
            seed = String.valueOf(System.currentTimeMillis());
        }
        return seed.hashCode();
    }

    @Override
    public void Save() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public String getName() {
        return name;
    }

    public ArrayList<DimensionInterface> getDimensions() {
        return dimensions;
    }

    public void AddDimension(DimensionInterface dimension) {
        this.dimensions.add(dimension);
    }

    public String getPath() {
        return path;
    }
}
