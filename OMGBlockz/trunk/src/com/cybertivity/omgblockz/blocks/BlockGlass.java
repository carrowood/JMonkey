package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockGlass extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(8, 0), true),
};
    private static BlockGlass instance = null;

    private BlockGlass() {
        super(SKINS);
        setAttributes();
    }

    protected BlockGlass(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 20;
        friendlyName = "Glass";
        commandName = "glass";
    }

    public static BlockGlass getInstance() {
        if (instance == null) {
            instance = new BlockGlass();
        }
        return instance;
    }
}
