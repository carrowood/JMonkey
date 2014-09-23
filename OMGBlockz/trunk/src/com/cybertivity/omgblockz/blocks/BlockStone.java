package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockStone extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(9, 0), false),
};
    private static BlockStone instance = null;

    private BlockStone() {
        super(SKINS);
        setAttributes();
    }

    protected BlockStone(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 1;
        friendlyName = "Stone";
        commandName = "stone";
    }

    public static BlockStone getInstance() {
        if (instance == null) {
            instance = new BlockStone();
        }
        return instance;
    }
}
