package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockGlass extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(8, 0), true),
    };

    public BlockGlass() {
        super(SKINS);

    }
}
