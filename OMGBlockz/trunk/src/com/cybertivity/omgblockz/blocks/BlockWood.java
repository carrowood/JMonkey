package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockWood extends BlockBase {

    protected static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(4, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(4, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false)
    };
    private static BlockWood instance = null;

    private BlockWood() {
        super(SKINS);
        setAttributes();
    }

    protected BlockWood(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 17;
        friendlyName = "Oak Wood";
        commandName = "log";
    }

    public static BlockWood getInstance() {
        if (instance == null) {
            instance = new BlockWood();
        }
        return instance;
    }
}
