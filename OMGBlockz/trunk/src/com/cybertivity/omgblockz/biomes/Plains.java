package com.cybertivity.omgblockz.biomes;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.utility.Coordinate;

public class Plains extends BiomeBase {

    public Plains() {
        temperature = 0.8f;
        biomeID = 1;
        name = "Plains";
        biome = Biome.PLAIN;
        allowedAdjacentBiomeIDs.add(biomeID);
    }

    public Chunk[][] GetChunkArray(int seed, Coordinate startingCoordinate, short worldHeight, short maxLandLevel, byte seaLevel) {
        if (startingCoordinate.getX() % Chunk.CHUNK_WIDTH_IN_BLOCKS != 0
                || startingCoordinate.getY() % Chunk.CHUNK_WIDTH_IN_BLOCKS != 0) {
            throw new IllegalArgumentException("Coordinate must be the lowest X and Z for a chunk (i.e. divisible by chunksize)");

        }
        Chunk chunk = new Chunk(startingCoordinate, worldHeight);

        //At startup, MC creates one region file containing 32X32 chunks.
        //On my machine this takes about 12 seconds.
        //my current impl 32x32 takes about 72s with only 3 layers
        //I am assuming that all chunks are in the file at the start but I may be wrong.
        //UPDATE! It only takes about 5s when I comment out blockTerrain.setBlock calls
        //so its not the formulas...
        Vector3Int numberOfChunks = new Vector3Int(8, 1, 8);

        //frequency - lower is choppier, higher is smoother
        //set the sea level on the top most layer
        Chunk[][] grass = generateSolidTerrain(seed, (short) 245, maxLandLevel, startingCoordinate,
                numberOfChunks, BlockGrass.getInstance().getBlockId(), worldHeight, seaLevel);
        //Note that now we change the amplitude to be lower
        //we also change the frequency so we dont have the same pattern on top of the previous layer
        Chunk[][] stone = generateSolidTerrain(seed, (short) 205, (short) 115, startingCoordinate,
                numberOfChunks, BlockStone.getInstance().getBlockId(), worldHeight);
        Chunk[][] bedrock = generateSolidTerrain(seed, (short) 3, (short) 4, startingCoordinate,
                numberOfChunks, BlockBrick.getInstance().getBlockId(), worldHeight);  //pretend its bedrock


        //Coordinate minXYZ = new Coordinate(0, 0, 0);
        //Coordinate maxXYZ = new Coordinate(chunkWidthBlocks * numberOfChunks.getX(), worldHeight, chunkWidthBlocks * numberOfChunks.getZ());
        //GenerateSparseTerrain(seed, 40f, minXYZ, maxXYZ, blockTerrain, Block_Wood.class);  //pretend its bedrock

        return chunk;
    }
}
