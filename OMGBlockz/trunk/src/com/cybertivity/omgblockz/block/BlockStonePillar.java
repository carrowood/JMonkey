package com.cybertivity.omgblockz.block;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockStonePillar extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(9, 0), false),};

    public BlockStonePillar() {
        super(SKINS);
        setShapes(new BlockShape_Cube(), new BlockShape_Pyramid());
    }

    @Override
    protected int getShapeIndex(BlockChunkControl chunk, Vector3Int location) {
        return (chunk.isBlockOnSurface(location) ? 1 : 0);
    }
}
