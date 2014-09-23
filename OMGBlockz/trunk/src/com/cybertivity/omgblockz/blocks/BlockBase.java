package com.cybertivity.omgblockz.blocks;

import com.cubes.*;

public abstract class BlockBase extends Block {

    protected short blockId = 0;
    protected String friendlyName = "";
    protected String commandName = "";
    protected byte variantId = 0;
    protected byte height = 1;
    protected byte width = 1;

    protected BlockBase(BlockSkin[] skins) {
        super(skins);
    }

    public byte getVariantId() {
        return variantId;
    }

    public byte getHeight() {
        return height;
    }

    public short getBlockId() {
        return blockId;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getCommandName() {
        return commandName;
    }
}
