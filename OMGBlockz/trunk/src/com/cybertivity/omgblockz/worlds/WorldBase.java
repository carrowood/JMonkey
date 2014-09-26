package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.*;
import com.cybertivity.omgblockz.utility.*;
import com.cybertivity.omgblockz.dimensions.*;
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

    public Chunk getChunk(Dimension dimension, Coordinate3D dimensionCoordinate) {
        DimensionInterface targetDimension = DetermineDimension(dimension);
        return targetDimension.getChunk(seed, dimensionCoordinate);
    }

    public Chunk[][] getChunkArray(Dimension dimension, Coordinate3D dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ) {
        DimensionInterface targetDimension = DetermineDimension(dimension);
        return targetDimension.getChunkArray(seed, dimensionCoordinate, arrayBoundsX, arrayBoundsZ);
    }

    @Override
    public void save() {
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

    public void addDimension(DimensionInterface dimension) {
        this.dimensions.add(dimension);
    }

    public String getPath() {
        return path;
    }

    private DimensionInterface DetermineDimension(Dimension dimension) {
        DimensionInterface targetDimension = null;
        for (int i = 0; i < dimensions.size(); i++) {
            DimensionInterface testDimension = dimensions.get(i);
            if (testDimension.getDimension() == dimension) {
                targetDimension = testDimension;
            }
        }
        if (targetDimension == null) {
            throw new UnsupportedOperationException("Unknown Dimension");
        }
        return targetDimension;
    }
}
