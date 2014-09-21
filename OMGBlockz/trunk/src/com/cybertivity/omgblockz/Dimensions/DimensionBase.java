/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybertivity.omgblockz.Dimensions;

import com.cybertivity.omgblockz.Utility.*;

public abstract class DimensionBase implements DimensionInterface {

    private int seed;
    private String path;
    private int worldHieght;
    private int maxLandheight;
    private int seaLevel;
    //private Chunk  playerChunk;

    public DimensionBase(int seed, String path, int worldHieght, int maxLandheight, int seaLevel) {
        this.seed = seed;
        this.path = path;
        this.worldHieght = worldHieght;
        this.maxLandheight = maxLandheight;
        this.seaLevel = seaLevel;
        FileSystemHelper.createDirectoryIfNeeded(path);
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
}
