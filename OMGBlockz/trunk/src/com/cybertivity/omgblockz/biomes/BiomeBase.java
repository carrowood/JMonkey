package com.cybertivity.omgblockz.biomes;

import com.cubes.*;
import com.cybertivity.omgblockz.Chunk;
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

    protected void generateSolidTerrain(int seed, short frequency, short amplitude,
            Chunk chunk, short blockId, short worldHeight) {

        generateSolidTerrain(seed, frequency, amplitude, chunk, blockId, worldHeight, (byte) -1);
    }

    protected void generateSolidTerrain(int seed, short frequency, short amplitude,
            Chunk chunk, short blockId, short worldHeight, byte seaLevel) {

        Perlin2D octave0 = new Perlin2D(seed, frequency);
        int dimensionPosX = chunk.getChunkCoordinateX() * Chunk.CHUNK_WIDTH_IN_BLOCKS;
        int y;
        int dimensionPosZ = chunk.getChunkCoordinateZ() * Chunk.CHUNK_WIDTH_IN_BLOCKS;
        double noise;

        Coordinate3D dimensionCoordinate = new Coordinate3D(dimensionPosX, 0, dimensionPosZ);


        //for every X,Z in chunk...
        for (int xOffset = 0; xOffset < Chunk.CHUNK_WIDTH_IN_BLOCKS; xOffset++) {
            for (int zOffset = 0; zOffset < Chunk.CHUNK_WIDTH_IN_BLOCKS; zOffset++) {
                //get the noise at X,Z to determine the height
                noise = amplitude * octave0.getNoiseLevelAtPosition(
                        (dimensionCoordinate.getX() + xOffset),
                        (dimensionCoordinate.getZ() + zOffset));

                //Set all blocks at X,Z from bottom up to noise level
                if (noise <= 0) {
                    chunk.setBlock(new Coordinate3D(xOffset, 0, zOffset), blockId);
                } else {
                    for (y = 0; y < noise; y++) {
                        if (y < worldHeight) {
                            chunk.setBlock(new Coordinate3D(xOffset, y, zOffset), blockId);
                        }
                    }
                    if (seaLevel > 0) {
                        short blockWaterID = BlockWater.getInstance().getBlockId();
                        while (y <= seaLevel) {
                            chunk.setBlock(new Coordinate3D(xOffset, y, zOffset), blockWaterID);
                            y++;
                        }
                    }
                }
            }
        }
    }
}
