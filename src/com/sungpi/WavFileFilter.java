package com.sungpi;

import java.io.File;
import java.io.FileFilter;

public class WavFileFilter implements FileFilter {
    private final String EXTENSION = "wav";

    public boolean accept(File file) {
        if (file.getName().toLowerCase().endsWith("wav")) {
            return true;
        }
        return false;
    }
}
