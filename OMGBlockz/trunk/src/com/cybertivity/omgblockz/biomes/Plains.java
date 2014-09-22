package com.cybertivity.omgblockz.biomes;

public class Plains extends BiomeBase {

    public Plains() {
        temperature = 0.8f;
        biomeID = 1;
        name = "Plains";
        biome = Biome.PLAIN;
        allowedAdjacentBiomeIDs.add(biomeID);
    }
}
