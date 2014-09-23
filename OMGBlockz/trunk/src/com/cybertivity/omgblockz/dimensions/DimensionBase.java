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
    private short worldHeight;
    private short maxLandheight;
    private short seaLevel;
    private Dimension dimension;
    protected static final ArrayList<BiomeInterface> biomes = new ArrayList<BiomeInterface>();

    public DimensionBase(Dimension dimension, int seed, String path, short worldHeight, short maxLandheight, short seaLevel) {
        this.dimension = dimension;
        this.seed = seed;
        this.path = path;
        this.worldHeight = worldHeight;
        this.maxLandheight = maxLandheight;
        this.seaLevel = seaLevel;
        FileSystemHelper.createDirectoryIfNeeded(path);
    }

    public Chunk getChunk(Coordinate coordinate) {
        Chunk chunk = checkDisk(coordinate);
        if (chunk == null) {
            BiomeManager biomeManager = BiomeManager.getInstance();
            BiomeInterface biome = biomeManager.GetBiome(seed, coordinate);
            chunk = biome.GetChunk(seed, coordinate, worldHeight, maxLandheight, seaLevel);
        }
        return chunk;
    }

    public short getWorldHeight() {
        return worldHeight;
    }

    public short getMaxLandheight() {
        return maxLandheight;
    }

    public short getSeaLevel() {
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
