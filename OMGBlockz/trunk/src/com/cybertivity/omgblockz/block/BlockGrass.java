package com.cybertivity.omgblockz.block;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockGrass extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(0, 0), false),
            new BlockSkin(new BlockSkin_TextureLocation(1, 0), false),
            new BlockSkin(new BlockSkin_TextureLocation(2, 0), false)
    };

    public BlockGrass() {
        super(SKINS);

    }
}
