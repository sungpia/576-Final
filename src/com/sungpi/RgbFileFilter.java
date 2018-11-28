package com.sungpi;

import java.io.File;
import java.io.FileFilter;

public class RgbFileFilter implements FileFilter {
    private final String EXTENSION = "rgb";

    public boolean accept(File file) {
        if (file.getName().toLowerCase().endsWith("rgb")) {
            return true;
        }
        return false;
    }
}
