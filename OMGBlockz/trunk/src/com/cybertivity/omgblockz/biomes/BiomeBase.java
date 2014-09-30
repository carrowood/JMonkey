package com.cybertivity.omgblockz.biomes;

import com.cubes.*;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.utility.*;
import java.util.ArrayList;
import org.j3d.texture.procedural.PerlinNoiseGenerator;

public abstract class BiomeBase implements BiomeInterface {

    protected float temperature = 0.8f;
    protected short biomeID = 1;
    protected String name = "Plains";
    protected ArrayList allowedAdjacentBiomeIDs = new ArrayList();
    public Biome biome = Biome.UNKNOWN;

    public ArrayList getAllowedAdjacentBiomeIDs() {
        return allowedAdjacentBiomeIDs;
    }

    protected short[][][] generateSolidTerrain(short[][][] blockIds, int seed, short frequency, short amplitude,
            short blockId, Coordinate3D dimensionCoordinate) {

        return generateSolidTerrain(blockIds, seed, frequency, amplitude, blockId, dimensionCoordinate, (byte) -1);
    }

    protected short[][][] generateSolidTerrain(short[][][] blockIds, int seed, short frequency, short amplitude,
            short blockId, Coordinate3D dimensionCoordinate, byte seaLevel) {

        Perlin2D octave0 = new Perlin2D(seed, frequency);
        int y;
        double noise;

        //for every X,Z in chunk...
        for (int xOffset = 0; xOffset < blockIds.length; xOffset++) {
            for (int zOffset = 0; zOffset < blockIds[0][0].length; zOffset++) {
                //get the noise at X,Z to determine the height
                noise = amplitude * octave0.getNoiseLevelAtPosition(
                        (dimensionCoordinate.getX() + xOffset),
                        (dimensionCoordinate.getZ() + zOffset));

                //just in case we get a negative
                if (noise <= 0) {
                    blockIds[xOffset][0][zOffset] = blockId;
                } else {
                    //Set all blocks at X,Z from bottom up to noise level
                    for (y = 0; y < noise; y++) {
                        if (y < blockIds[0].length) {
                            blockIds[xOffset][y][zOffset] = blockId;
                        }
                    }
                    if (seaLevel > 0) {
                        short blockWaterID = BlockWater.getInstance().getBlockId();
                        while (y <= seaLevel) {
                            blockIds[xOffset][y][zOffset] = blockWaterID;
                            y++;
                        }
                    }
                }
            }
        }

        return blockIds;
    }
}
