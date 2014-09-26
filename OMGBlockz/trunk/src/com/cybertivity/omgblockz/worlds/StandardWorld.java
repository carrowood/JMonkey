package com.cybertivity.omgblockz.worlds;

import com.cybertivity.omgblockz.dimensions.*;
import com.cybertivity.omgblockz.utility.Coordinate3D;
import java.io.IOException;

public class StandardWorld extends WorldBase {

    public StandardWorld(String seed, String path, String name) throws IOException {
        super(seed, path, name);

        Overworld overworld = new Overworld(super.getSeed(), this.getPath());
        Nether nether = new Nether(super.getSeed(), this.getPath());
        TheEnd theEnd = new TheEnd(super.getSeed(), this.getPath());
        this.addDimension(overworld);
        this.addDimension(nether);
        this.addDimension(theEnd);
    }
}
