package com.cybertivity.omgblockz.biomes;

import com.cubes.*;
import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.utility.Coordinate;
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

    protected Chunk[][] generateSolidTerrain(int seed, short frequency, short amplitude,
            Coordinate minXY, Vector3Int numberOfChunks, short blockId, short worldHeight) {

        return generateSolidTerrain(seed, frequency, amplitude, minXY, numberOfChunks, blockId, worldHeight, (byte) -1);
    }

    protected Chunk[][] generateSolidTerrain(int seed, short frequency, short amplitude,
            Coordinate minXY, Vector3Int numberOfChunks, short blockId, short worldHeight, byte seaLevel) {

        PerlinNoiseGenerator octave0 = new PerlinNoiseGenerator(seed);
        int y;
        double noise;
        Chunk[][] chunks = new Chunk[numberOfChunks.getX()][numberOfChunks.getZ()];

//        Coordinate maxXZ = new Coordinate(Chunk.CHUNK_WIDTH_IN_BLOCKS * numberOfChunks.getX(),
//                0, Chunk.CHUNK_WIDTH_IN_BLOCKS * numberOfChunks.getZ());

        for (int chunkRealtivePosX = 0; chunkRealtivePosX < numberOfChunks.getX(); chunkRealtivePosX++) {
            for (int chunkRealtivePosZ = 0; chunkRealtivePosZ < numberOfChunks.getX(); chunkRealtivePosZ++) {
                Coordinate chunkStartPos = new Coordinate(minXY.getX() + (chunkRealtivePosX * Chunk.CHUNK_WIDTH_IN_BLOCKS), 0, minXY.getZ() + (chunkRealtivePosZ * Chunk.CHUNK_WIDTH_IN_BLOCKS));
                Chunk chunk = new Chunk(chunkStartPos, worldHeight);

                //for every X,Z in chunk...
                for (int xPos = 0; xPos < Chunk.CHUNK_WIDTH_IN_BLOCKS; xPos++) {
                    for (int zPos = 0; zPos < Chunk.CHUNK_WIDTH_IN_BLOCKS; zPos++) {
                        //get the noise at X,Z to determine the height
                        noise = amplitude * octave0.noise2(chunkStartPos.getX() + xPos,
                                chunkStartPos.getZ() + zPos);

                        //Set all blocks at X,Z from bottom up to noise level
                        for (y = 0; y < noise; y++) {
                            chunk.setBlock(new Coordinate(xPos, y, zPos), blockId);
                        }
                        if (seaLevel > 0) {
                            short blockWaterID = BlockWater.getInstance().getBlockId();
                            while (y <= seaLevel) {
                                chunk.setBlock(new Coordinate(xPos, y, zPos), blockWaterID);
                                y++;
                            }
                        }
                    }
                }

                chunks[chunkRealtivePosX][chunkRealtivePosZ] = chunk;
            }

        }
        return chunks;
    }
}
