package com.cybertivity.omgblockz.block;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockConnectorRod extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(7, 0), false),};

    public BlockConnectorRod() {
        super(SKINS);
        setShapes(
                new BlockShape_Cuboid(new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f}),
                new BlockShape_Cuboid(new float[]{0.5f, 0.5f, 0.2f, 0.2f, 0.2f, 0.2f}),
                new BlockShape_Cuboid(new float[]{0.2f, 0.2f, 0.5f, 0.5f, 0.2f, 0.2f}),
                new BlockShape_Cuboid(new float[]{0.2f, 0.2f, 0.2f, 0.2f, 0.5f, 0.5f}));
    }

    @Override
    protected int getShapeIndex(BlockChunkControl chunk, Vector3Int location) {
        if ((chunk.getNeighborBlock_Global(location, Block.Face.Top) != null) && (chunk.getNeighborBlock_Global(location, Block.Face.Bottom) != null)) {
            return 1;
        } else if ((chunk.getNeighborBlock_Global(location, Block.Face.Left) != null) && (chunk.getNeighborBlock_Global(location, Block.Face.Right) != null)) {
            return 2;
        } else if ((chunk.getNeighborBlock_Global(location, Block.Face.Front) != null) && (chunk.getNeighborBlock_Global(location, Block.Face.Back) != null)) {
            return 3;
        }
        return 0;
    }
}
