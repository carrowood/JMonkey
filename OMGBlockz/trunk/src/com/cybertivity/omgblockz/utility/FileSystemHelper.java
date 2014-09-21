package com.cybertivity.omgblockz.utility;

import java.io.File;

public class FileSystemHelper {

    public static void createDirectoryIfNeeded(String directoryName) {
        File theDir = new File(directoryName);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
    }

    public static String GetFileSeparator(){
        return File.separator;
    }
}
