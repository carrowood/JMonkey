package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockGrass extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(0, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(1, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(2, 0), false)
    };
    private static BlockGrass instance = null;

    private BlockGrass() {
        super(SKINS);
        setAttributes();
    }

    protected BlockGrass(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 2;
        friendlyName = "Grass";
        commandName = "grass";
    }

    public static BlockGrass getInstance() {
        if (instance == null) {
            instance = new BlockGrass();
        }
        return instance;
    }
}
