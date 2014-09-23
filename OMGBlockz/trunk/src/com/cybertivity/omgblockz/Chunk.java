package com.cybertivity.omgblockz;

import com.cybertivity.omgblockz.blocks.*;
import com.cybertivity.omgblockz.utility.Coordinate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Chunk {

    private static final Logger log = Logger.getLogger(Chunk.class.getName());
    public static final byte CHUNK_WIDTH_IN_BLOCKS = 16;
    protected static final HashMap<Integer, HashMap<Integer, Chunk>> loadedChunksByPosX = new HashMap<Integer, HashMap<Integer, Chunk>>();
    private boolean hasUnsavedChanges = true;
    private Date lastModified = new Date();
    private Date lastAccessed = new Date();
    private String saveDirectoryPath;

    private static Chunk loadfromDisk(int chunkCoordinateX, int chunkCoordinateZ) {
        //TODO Chunk checkDisk(int chunkCoordinateX, int chunkCoordinateZ)
        return null;
    }

    private static void saveToDisk(Chunk chunk) {
        //TODO Chunk checkDisk(int chunkCoordinateX, int chunkCoordinateZ)
        chunk.hasUnsavedChanges = false;
    }
    private short[][][] blockIDs;
    private int chunkCoordinateX;
    private int chunkCoordinateZ;

    public Chunk(Coordinate blockCoordinateInDimension, short worldHeight, String saveDirectoryPath) {
        blockIDs = new short[CHUNK_WIDTH_IN_BLOCKS][worldHeight][CHUNK_WIDTH_IN_BLOCKS];
        chunkCoordinateX = getChunkCoordinateXFromDimensionCoordinateX(blockCoordinateInDimension);
        chunkCoordinateZ = getChunkCoordinateZFromDimensionCoordinateZ(blockCoordinateInDimension);
        this.saveDirectoryPath = saveDirectoryPath;
    }

    public Chunk(int chunkCoordinateX, int chunkCoordinateZ, short worldHeight, String saveDirectoryPath) {
        blockIDs = new short[CHUNK_WIDTH_IN_BLOCKS][worldHeight][CHUNK_WIDTH_IN_BLOCKS];
        chunkCoordinateX = chunkCoordinateX;
        chunkCoordinateZ = chunkCoordinateZ;
        this.saveDirectoryPath = saveDirectoryPath;
    }

    public static int getChunkCoordinateXFromDimensionCoordinateX(Coordinate dimensionCoordinate) {
        return (int) (float) dimensionCoordinate.x / Chunk.CHUNK_WIDTH_IN_BLOCKS;
    }

    public static int getChunkCoordinateZFromDimensionCoordinateZ(Coordinate dimensionCoordinate) {
        return (int) (float) dimensionCoordinate.z / Chunk.CHUNK_WIDTH_IN_BLOCKS;
    }

    public BlockBase getBlock(Coordinate coordinateWithInChunk) {
        short blockID = blockIDs[coordinateWithInChunk.getX()][coordinateWithInChunk.getY()][coordinateWithInChunk.getZ()];
        lastAccessed = new Date();
        return MyBlockManager.GetInstanceByBlockID(blockID);
    }

    public void setBlock(Coordinate coordinateWithInChunk, short blockId) {
        blockIDs[coordinateWithInChunk.getX()][coordinateWithInChunk.getY()][coordinateWithInChunk.getZ()] = blockId;
        hasUnsavedChanges = true;
        lastModified = new Date();
        lastAccessed = new Date();        
    }

    /**
     * Attempts to locate the chunk for the given position in both the memory
     * cache and on disk.
     *
     * @param chunkCoordinateX
     * @param chunkCoordinateZ
     * @return The chunk at the given position or null if it has not yet been
     * created.
     */
    public static Chunk getExistingChunk(int chunkCoordinateX, int chunkCoordinateZ) {
        Chunk chunk = null;

        HashMap<Integer, Chunk> chunkMapByZ = loadedChunksByPosX.get(chunkCoordinateX);
        if (chunkMapByZ == null) {
            chunkMapByZ = new HashMap<Integer, Chunk>();
            loadedChunksByPosX.put(chunkCoordinateX, chunkMapByZ);
        }
        chunk = chunkMapByZ.get(chunkCoordinateZ);
        if (chunk == null) {
            chunk = Chunk.loadfromDisk(chunkCoordinateX, chunkCoordinateZ);
        }
        return chunk;
    }

    public int getChunkCoordinateX() {
        return chunkCoordinateX;
    }

    public int getChunkCoordinateZ() {
        return chunkCoordinateZ;
    }

    private class PersistanceManager {

        final Logger log = Logger.getLogger(PersistanceManager.class.getName());
        Timer timer = new Timer(true);
        int allowedTimeForUnsavedData = 60 * 1000; //5 mins
        int maxTimeToCache = 300 * 1000; //5 mins

        public PersistanceManager() {
            timer.schedule(
                    new TimerTask() {
                public void run() {
                    SaveModifiedChunks();
                }
            }, 60 * 1000, 60 * 1000);
        }

        public void SaveModifiedChunks() {
            log.fine("Saving Modified Chunks");
            Iterator iter1 = loadedChunksByPosX.entrySet().iterator();
            Date now = new Date();
            long differenceInMillis = 0;
            while (iter1.hasNext()) {
                Map.Entry pairs1 = (Map.Entry) iter1.next();
                HashMap<Integer, Chunk> chunkMapByZ = (HashMap<Integer, Chunk>) pairs1.getValue();
                for (Map.Entry pairs2 : chunkMapByZ.entrySet()) {
                    Chunk chunk = (Chunk) pairs2.getValue();
                    if (chunk.hasUnsavedChanges) {
                        differenceInMillis = now.getTime() - chunk.lastModified.getTime();
                        if(differenceInMillis>allowedTimeForUnsavedData){
                            Chunk.saveToDisk(chunk);                            
                            chunk.hasUnsavedChanges=false;
                        }
                    }else{
                        
                    }
                }sdfsfdsd
            }
        }
    }
}
}
