package com.cybertivity.omgblockz.block;

import com.cubes.*;

public class BlockWood extends Block {

    protected static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(4, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(4, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(3, 0), false)
    };

    public BlockWood() {
        super(SKINS);
    }
    protected BlockWood(BlockSkin[] skins) {
        super(skins);
    }
}
