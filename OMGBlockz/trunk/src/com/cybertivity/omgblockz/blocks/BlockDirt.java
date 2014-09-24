package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockDirt extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(2, 0), false)
    };
    private static BlockDirt instance = null;

    private BlockDirt() {
        super(SKINS);
        setAttributes();
    }

    protected BlockDirt(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 3;
        friendlyName = "Dirt";
        commandName = "dirt";
    }

    public static BlockDirt getInstance() {
        if (instance == null) {
            instance = new BlockDirt();
        }
        return instance;
    }
}
