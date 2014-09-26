package com.cybertivity.omgblockz.biomes;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
import com.cybertivity.omgblockz.Chunk;
import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.dimensions.DimensionBase;
import com.cybertivity.omgblockz.utility.Coordinate3D;

public class Plains extends BiomeBase {

    public Plains() {
        temperature = 0.8f;
        biomeID = 1;
        name = "Plains";
        biome = Biome.PLAIN;
        allowedAdjacentBiomeIDs.add(biomeID);
    }

    public Chunk[][] getChunkArray(int seed, int chunkCoordinateX, int chunkCoordinateZ, int arrayBoundsX, int arrayBoundsZ, DimensionBase dimension) {
        Chunk[][] chunks = new Chunk[arrayBoundsX][arrayBoundsZ];
        Chunk chunk;
        for (int x = 0; x < arrayBoundsX; x++) {
            for (int z = 0; z < arrayBoundsZ; z++) {
                chunks[x][z] = GetChunk(seed, chunkCoordinateX + x, chunkCoordinateZ + z, dimension);
            }
        }
        return chunks;
    }

    public Chunk GetChunk(int seed, int chunkCoordinateX, int chunkCoordinateZ,
            DimensionBase dimension) {
        Chunk chunk = Chunk.getExistingChunk(chunkCoordinateX, chunkCoordinateZ);
        if (chunk == null) {

            chunk = new Chunk(chunkCoordinateX, chunkCoordinateZ, dimension.getWorldHeight(), dimension.getPath());

            //frequency - lower is choppier, higher is smoother
            //set the sea level on the top most layer
            generateSolidTerrain(seed, (short) 220, (short) 80,
                    chunk, BlockGrass.getInstance().getBlockId(), dimension.getWorldHeight(), dimension.getSeaLevel());
            //Note that now we change the amplitude to be lower
            //we also change the frequency so we dont have the same pattern on top of the previous layer
            generateSolidTerrain(seed, (short) 150, (short) 72,
                    chunk, BlockStone.getInstance().getBlockId(), dimension.getWorldHeight());

            //TODO: Replace brick with Bedrock
            generateSolidTerrain(seed, (short) 3, (short) 4,
                    chunk, BlockBrick.getInstance().getBlockId(), dimension.getWorldHeight());


            //TODO: GenerateSparseTerrain
            //GenerateSparseTerrain(seed, 40f, minXYZ, maxXYZ, blockTerrain, Block_Wood.class);  //pretend its bedrock


        }
        return chunk;
    }
}
