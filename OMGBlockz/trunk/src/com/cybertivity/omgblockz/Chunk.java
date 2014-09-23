package com.cybertivity.omgblockz;

import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.utility.Coordinate;

public class Chunk {

    public static final byte CHUNK_WIDTH_IN_BLOCKS = 16;
    private short[][][] blockIDs;
    private Coordinate coordinateInDimension; //Z should always be 0

    public Chunk(Coordinate dimensionCoordinate, short worldHeight) {
        blockIDs = new short[CHUNK_WIDTH_IN_BLOCKS][worldHeight][CHUNK_WIDTH_IN_BLOCKS];
        this.coordinateInDimension = coordinateInDimension;
    }

    public BlockBase getBlock(Coordinate coordinate) {
        short blockID = blockIDs[coordinate.getX()][coordinate.getY()][coordinate.getZ()];
        return MyBlockManager.GetInstanceByBlockID(blockID);
    }

    public overlayBlocks(short[][][] newBlockIDs) {
        //TODO: Make sure the arrays are the same size when overlaying...
        for (int i = 0; i < blockIDs.; i++) {
            B[i] = A[i]
        }
    }

    public void setBlock(Coordinate coordinate, short blockId) {
        blockIDs[coordinate.getX()][coordinate.getY()][coordinate.getZ()] = blockId;
    }

    public Coordinate getCoordinateInDimension() {
        return coordinateInDimension;
    }
}
