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
        instance.variantId = 0;
        instance.height = 1;
        instance.width = 1;
        instance.blockId = 45;
        instance.friendlyName = "Bricks";
        instance.commandName = "brick_block";
    }
}
