package com.sungpi.ImportFileUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class VideoFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = ImportFileUtil.getExtension(f);
        if (extension != null) {
            if (extension.equals(ImportFileUtil.wav) ||
                extension.equals(ImportFileUtil.rgb)) {
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() { return "Just Videos"; }
}
