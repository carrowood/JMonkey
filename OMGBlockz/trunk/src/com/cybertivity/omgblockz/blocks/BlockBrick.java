package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockBrick extends BlockBase {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(7, 0), false),};
    private static BlockBrick instance = null;

    private BlockBrick() {
        super(SKINS);
        setAttributes();
    }

    protected BlockBrick(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    public static BlockBrick getInstance() {
        if (instance == null) {
            instance = new BlockBrick();
        }
        return instance;
    }

    private void setAttributes() {
        variantId = 0;
        height = 1;
        width = 1;
        blockId = 45;
        friendlyName = "Bricks";
        commandName = "brick_block";
    }
}
