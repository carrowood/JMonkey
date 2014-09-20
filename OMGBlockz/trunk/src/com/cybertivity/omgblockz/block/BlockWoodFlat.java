package com.cybertivity.omgblockz.block;

import com.cubes.*;
import com.cubes.shapes.*;
import static com.cybertivity.omgblockz.block.BlockWood.SKINS;

public class BlockWoodFlat extends BlockWood {

    public BlockWoodFlat() {
        super(SKINS);
        setShapes(new BlockShape_Cuboid(
                new float[]{
            0, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f}));
    }
}
