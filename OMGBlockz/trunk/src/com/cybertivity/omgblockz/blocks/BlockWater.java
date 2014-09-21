package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import com.cubes.shapes.*;

public class BlockWater extends Block {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
            new BlockSkin(new BlockSkin_TextureLocation(0, 1), true),
    };

    public BlockWater() {
        super(SKINS);

    }
}
