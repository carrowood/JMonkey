/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybertivity.omgblockz.dimensions;

import com.cybertivity.omgblockz.biomes.BiomeInterface;
import com.cybertivity.omgblockz.biomes.BiomeManager;
import com.cybertivity.omgblockz.utility.*;
import java.util.ArrayList;

public abstract class DimensionBase implements DimensionInterface {

    private int seed;
    private String path;
    private short worldHeight;
    private short maxLandheight;
    private byte seaLevel;
    private Dimension dimension;
    protected static final ArrayList<BiomeInterface> biomes = new ArrayList<BiomeInterface>();

    public DimensionBase(Dimension dimension, int seed, String path, short worldHeight, short maxLandheight, byte seaLevel) {
        this.dimension = dimension;
        this.seed = seed;
        this.path = path;
        this.worldHeight = worldHeight;
        this.maxLandheight = maxLandheight;
        this.seaLevel = seaLevel;
        FileSystemHelper.createDirectoryIfNeeded(path);
    }

    public short[][][] getTerrain(int seed, Coordinate3D dimensionCoordinate, int blockCountX, int blockCountZ) {

        BiomeManager biomeManager = BiomeManager.getInstance();
        BiomeInterface biome = biomeManager.GetBiome(seed, dimensionCoordinate);
        short[][][] blockIDs = new short[blockCountX][this.getWorldHeight()][blockCountZ];
        return biome.getTerrain(blockIDs, seed, dimensionCoordinate.x, dimensionCoordinate.z, this);
    }

//    public Chunk[][] getChunkArray(int seed, Coordinate3D dimensionCoordinate, int arrayBoundsX, int arrayBoundsZ) {
//        int chunkCoordinateX = Chunk.getChunkCoordinateXFromDimensionCoordinateX(dimensionCoordinate);
//        int chunkCoordinateZ = Chunk.getChunkCoordinateZFromDimensionCoordinateZ(dimensionCoordinate);
//
//        Chunk[][] chunks = new Chunk[arrayBoundsX][arrayBoundsZ];
//        Coordinate3D coordinateToGet = new Coordinate3D(0, 0, 0);
//        for (int x = 0; x < arrayBoundsX; x++) {
//            for (int z = 0; z < arrayBoundsZ; z++) {
//                coordinateToGet.x = chunkCoordinateX + (x * Chunk.CHUNK_WIDTH_IN_BLOCKS);
//                coordinateToGet.z = chunkCoordinateZ + (z * Chunk.CHUNK_WIDTH_IN_BLOCKS);
//                chunks[x][z] = getChunk(seed, coordinateToGet);
//            }
//        }
//        return chunks;
//    }
    public short getWorldHeight() {
        return worldHeight;
    }

    public short getMaxLandheight() {
        return maxLandheight;
    }

    public byte getSeaLevel() {
        return seaLevel;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getSeed() {
        return seed;
    }

    public String getPath() {
        return path;
    }
}
