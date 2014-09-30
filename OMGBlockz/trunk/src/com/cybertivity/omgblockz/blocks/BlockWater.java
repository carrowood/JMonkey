package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockWater extends BlockBase {

    private static BlockWater instance = null;

    private BlockWater() {
        super(new BlockSkin(new BlockSkin_TextureLocation(0, 1), true));
        setAttributes();
    }

    protected BlockWater(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 9;
        friendlyName = "Water";
        commandName = "water";
    }

    public static BlockWater getInstance() {
        if (instance == null) {
            instance = new BlockWater();
        }
        return instance;
    }
}
