/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.biomes.BiomeInterface;
import com.cybertivity.omgblockz.biomes.BiomeManager;
import com.cybertivity.omgblockz.utility.*;
import java.util.ArrayList;

public abstract class DimensionBase implements DimensionInterface {

    private int seed;
    private String path;
    private int worldHieght;
    private int maxLandheight;
    private int seaLevel;
    private Dimension dimension;
    protected static final ArrayList<BiomeInterface> biomes = new ArrayList<BiomeInterface>();

    public DimensionBase(Dimension dimension, int seed, String path, int worldHieght, int maxLandheight, int seaLevel) {
        this.dimension = dimension;
        this.seed = seed;
        this.path = path;
        this.worldHieght = worldHieght;
        this.maxLandheight = maxLandheight;
        this.seaLevel = seaLevel;
        FileSystemHelper.createDirectoryIfNeeded(path);
    }

    public Chunk getChunk(Coordinate coordinate) {
        Chunk chunk = checkDisk(coordinate);
        if (chunk == null) {
            BiomeManager biomeManager = BiomeManager.getInstance();
            BiomeInterface biome = biomeManager.GetBiome(seed, coordinate);
            chunk = biome.GetChunk(seed, coordinate);
        }
        return chunk;
    }

    public int getWorldHieght() {
        return worldHieght;
    }

    public int getMaxLandheight() {
        return maxLandheight;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public Dimension getDimension() {
        return dimension;
    }

    private Chunk checkDisk(Coordinate coordinate) {
        //TODO Chunk checkDisk(Coordinate coordinate)
        return null;
    }
}
