/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybertivity.omgblockz;

import com.cybertivity.omgblockz.blocks.*;
import com.cubes.BlockManager;

public class BlockManagerImpl extends BlockManager {

    public final BlockGrass BLOCK_GRASS = new BlockGrass();
    public final BlockWood BLOCK_WOOD = new BlockWood();
    public final BlockWoodFlat BLOCK_WOOD_FLAT = new BlockWoodFlat();
    public final BlockBrick BLOCK_BRICK = new BlockBrick();
    public final BlockConnectorRod BLOCK_CONNECTOR_ROD = new BlockConnectorRod();
    public final BlockGlass BLOCK_GLASS = new BlockGlass();
    public final BlockStone BLOCK_STONE = new BlockStone();
    public final BlockStonePillar BLOCK_STONE_PILLAR = new BlockStonePillar();
    public final BlockWater BLOCK_WATER = new BlockWater();

    public BlockManagerImpl() {
        BlockManager.register(BLOCK_GRASS);
        BlockManager.register(BLOCK_WOOD);
        BlockManager.register(BLOCK_WOOD_FLAT);
        BlockManager.register(BLOCK_BRICK);
        BlockManager.register(BLOCK_CONNECTOR_ROD);
        BlockManager.register(BLOCK_GLASS);
        BlockManager.register(BLOCK_STONE);
        BlockManager.register(BLOCK_STONE_PILLAR);
        BlockManager.register(BLOCK_WATER);
    }
}
