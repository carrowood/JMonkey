package com.cybertivity.omgblockz.blocks;

import com.cubes.*;
import java.util.*;

public class MyBlockManager extends BlockManager {

    private static HashMap<Short, BlockBase> BLOCKS = new HashMap<Short, BlockBase>();
    public final BlockGrass BLOCK_GRASS = BlockGrass.getInstance();
    public final BlockWood BLOCK_WOOD = BlockWood.getInstance();
    public final BlockBrick BLOCK_BRICK = BlockBrick.getInstance();
    public final BlockConnectorRod BLOCK_CONNECTOR_ROD = BlockConnectorRod.getInstance();
    public final BlockGlass BLOCK_GLASS = BlockGlass.getInstance();
    public final BlockStone BLOCK_STONE = BlockStone.getInstance();
    public final BlockStonePillar BLOCK_STONE_PILLAR = BlockStonePillar.getInstance();
    public final BlockWater BLOCK_WATER = BlockWater.getInstance();

    public MyBlockManager() {
        RegisterBlock(BLOCK_GRASS);
        RegisterBlock(BLOCK_WOOD);
        RegisterBlock(BLOCK_BRICK);
        RegisterBlock(BLOCK_CONNECTOR_ROD);
        RegisterBlock(BLOCK_GLASS);
        RegisterBlock(BLOCK_STONE);
        RegisterBlock(BLOCK_STONE_PILLAR);
        RegisterBlock(BLOCK_WATER);
    }

    private void RegisterBlock(BlockBase block) {
        BLOCKS.put(block.getBlockId(), block);
        BlockManager.register(block);
    }

    /***
     * Will return NULL for air;
     * @param blockID
     * @return
     */
    public static BlockBase GetInstanceByBlockID(short blockID) {
        return BLOCKS.get(blockID);
    }
}
