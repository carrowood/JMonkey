package com.cybertivity.omgblockz.block;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockBrick extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(7, 0), false),
    };

    public BlockBrick() {
        super(SKINS);

    }
}
