package com.cybertivity.omgblockz.biomes;

import com.cubes.BlockTerrainControl;
import com.cubes.Vector3Int;
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

//    public Chunk[][] getChunkArray(int seed, int chunkCoordinateX, int chunkCoordinateZ, int arrayBoundsX, int arrayBoundsZ, DimensionBase dimension) {
//        Chunk[][] chunks = new Chunk[arrayBoundsX][arrayBoundsZ];
//        Chunk chunk;
//        for (int x = 0; x < arrayBoundsX; x++) {
//            for (int z = 0; z < arrayBoundsZ; z++) {
//                chunks[x][z] = GetChunk(seed, chunkCoordinateX + x, chunkCoordinateZ + z, dimension);
//            }
//        }
//        return chunks;
//    }
    public short[][][] getTerrain(short[][][] blockIds, int seed, int dimensionCoordinateX, int dimensionCoordinateZ, DimensionBase dimension) {

        Coordinate3D dimensionCoordinate = new Coordinate3D(dimensionCoordinateX, 0, dimensionCoordinateZ);
        //boolean loadedFromFile = Region.getExistingChunk(chunkCoordinateX, chunkCoordinateZ);
        //if (chunk == null) {

        //chunk = new Chunk(chunkCoordinateX, chunkCoordinateZ, dimension.getWorldHeight(), dimension.getPath());

        //frequency - lower is choppier, higher is smoother
        //set the sea level on the top most layer
        blockIds = generateSolidTerrain(blockIds, seed, (short) 245, (short) dimension.getMaxLandheight(),
                BlockGrass.getInstance().getBlockId(), dimensionCoordinate, dimension.getSeaLevel());
        //Note that now we change the amplitude to be lower
        //we also change the frequency so we dont have the same pattern on top of the previous layer
        blockIds = generateSolidTerrain(blockIds, seed, (short) 205, (short) 115,
                BlockStone.getInstance().getBlockId(), dimensionCoordinate);

        //TODO: Replace brick with Bedrock
        blockIds = generateSolidTerrain(blockIds, seed, (short) 3, (short) 4,
                BlockBrick.getInstance().getBlockId(), dimensionCoordinate);


        //TODO: GenerateSparseTerrain
        //GenerateSparseTerrain(seed, 40f, minXYZ, maxXYZ, blockTerrain, Block_Wood.class);  //pretend its bedrock

        //}
        return blockIds;
    }
}
