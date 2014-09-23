package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockStonePillar extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(9, 0), false),};
    private static BlockStonePillar instance = null;

    private BlockStonePillar() {
        super(SKINS);
        setAttributes();
    }

    protected BlockStonePillar(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;//TODO: Is height and width right on this block?
        width = 1;
        blockId = 5001;
        friendlyName = "Stone Pillar";
        commandName = "stone_pillar";
        setShapes(new BlockShape_Cube(), new BlockShape_Pyramid());
    }

    public static BlockStonePillar getInstance() {
        if (instance == null) {
            instance = new BlockStonePillar();
        }
        return instance;
    }

    @Override
    protected int getShapeIndex(BlockChunkControl chunk, Vector3Int location) {
        return (chunk.isBlockOnSurface(location) ? 1 : 0);
    }
}
