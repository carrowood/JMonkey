package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public class BlockCoarseDirt extends BlockDirt {

    private static final BlockSkin[] SKINS = new BlockSkin[]{
        new BlockSkin(new BlockSkin_TextureLocation(2, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(2, 0), false),
        new BlockSkin(new BlockSkin_TextureLocation(6, 0), false) //TODO: BlockCoarseDirt Fixme with a single texture for all sides
    };
    private static BlockCoarseDirt instance = null;

    private BlockCoarseDirt() {
        super(SKINS);
        setAttributes();
    }

    protected BlockCoarseDirt(BlockSkin[] skins) {
        super(skins);
        setAttributes();
    }

    private void setAttributes() {
        instance.variantId = 1;
        instance.friendlyName = "Coarse Dirt";
    }

    public static BlockCoarseDirt getInstance() {
        if (instance == null) {
            instance = new BlockCoarseDirt();
        }
        return instance;
    }
}
