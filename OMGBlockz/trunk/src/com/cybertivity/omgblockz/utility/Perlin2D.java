package com.cybertivity.omgblockz.utility;

/*
 * Perlin noise octave generator v1.0
 * Created by Baggerboot; 3/5/2012
 * http://www.minecraftforum.net/forums/off-topic/computers-science-technology/482027-generating-perlin-noise?page=4
 */
import java.util.Random;

public class Perlin2D {

    private long seed;
    private Random rand;
    private int octave;
    //BUGFREE

    public Perlin2D(long seed, int octave) {
        this.seed = seed;
        this.octave = octave;
        rand = new Random();
    }

    public double getNoiseLevelAtPosition(int x, int z) {
        int xmin = (int) (double) x / octave;
        int xmax = xmin + 1;
        int zmin = (int) (double) z / octave;
        int zmax = zmin + 1;
        Coordinate a = new Coordinate(xmin, zmin);
        Coordinate b = new Coordinate(xmax, zmin);
        Coordinate c = new Coordinate(xmax, zmax);
        Coordinate d = new Coordinate(xmin, zmax);
        double ra = getRandomAtPosition(a);
        double rb = getRandomAtPosition(b);
        double rc = getRandomAtPosition(c);
        double rd = getRandomAtPosition(d);
        double ret = cosineInterpolate( //Interpolate Z direction
                cosineInterpolate((float) ra, (float) rb, (float) (x - xmin * octave) / octave), //Interpolate X1
                cosineInterpolate((float) rd, (float) rc, (float) (x - xmin * octave) / octave), //Interpolate X2
                ((float) z - (float) zmin * (float) octave) / (float) octave);
        return ret;
    }

    private float cosineInterpolate(float a, float b, float x) {
        float ft = (float) (x * Math.PI);
        float f = (float) ((1f - Math.cos(ft)) * .5f);
        float ret = a * (1f - f) + b * f;
        return ret;
    }

    private double getRandomAtPosition(Coordinate coord) {
        double var = 10000 * (Math.sin(coord.getX()) + Math.cos(coord.getZ()) + Math.tan(seed));
        rand.setSeed((long) var);
        double ret = rand.nextDouble();
        return ret;
    }

    private class Coordinate {

        private int x, z;

        public Coordinate(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public void set(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }
}
