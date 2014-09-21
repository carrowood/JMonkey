package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockStone extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(9, 0), false),
    };

    public BlockStone() {
        super(SKINS);

    }
}
