package com.sungpi.ImportFileUtils;


import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFileView.java is used by FileChooserDemo2.java. */
public class VideoFrameView extends FileView {

    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }

    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }

    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

    // TODO(sungwonc): We only import folders. fix it accordingly.
    public String getTypeDescription(File f) {
        String extension = ImportFileUtil.getExtension(f);
        String type = null;

        if (extension != null) {

        }
        return type;
    }

    // TODO(sungwonc): Grab first .rgb file from the folder and display it.
    public Icon getIcon(File f) {
        String extension = ImportFileUtil.getExtension(f);
        Icon icon = null;

        if (extension != null) {

        }
        return icon;
    }
}
