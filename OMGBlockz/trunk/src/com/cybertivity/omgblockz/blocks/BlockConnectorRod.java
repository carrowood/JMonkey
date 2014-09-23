package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockConnectorRod extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(7, 0), false),};
    private static BlockConnectorRod instance = null;

    private BlockConnectorRod() {
        super(SKINS);
        setAttributes();
    }

    protected BlockConnectorRod(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1; //TODO: Is height and width right on this block?
        width = 1;
        blockId = 5000;
        friendlyName = "Connector Rod";
        commandName = "rod";
    }

    public static BlockConnectorRod getInstance() {
        if (instance == null) {
            instance = new BlockConnectorRod();
        }
        return instance;
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
