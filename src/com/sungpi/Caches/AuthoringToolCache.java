package com.sungpi.Caches;

import com.sungpi.InteractiveVideo;
import com.sungpi.InteractiveVideoPlayerUtils.InteractiveVideoPlayerOption;

import java.io.File;

public class AuthoringToolCache {
    private static final AuthoringToolCache instance = new AuthoringToolCache();

    private InteractiveVideo video1;
    private InteractiveVideo video2;

    private File video1Folder = null;
    private File video2Folder = null;

    private File[] video1Files = new File[9000];
    private File[] video2Files = new File[9000];

    private int Video1Frame = 0;
    private int Video2Frame = 0;

    public int getVideo1Frame() {
        return Video1Frame;
    }

    public void setVideo1Frame(int video1Frame) {
        Video1Frame = video1Frame;
    }

    public int getVideo2Frame() {
        return Video2Frame;
    }

    public void setVideo2Frame(int video2Frame) {
        Video2Frame = video2Frame;
    }

    private AuthoringToolCache() {}

    public static AuthoringToolCache getInstance() {
        return instance;
    }

    public void setPrimaryVideoFile(File[] file) {
        this.video1Files = file;
    }

    public File[] getPrimaryVideoFiles() {
        return this.video1Files;
    }

    public File getPrimaryVideoFile(int frameNumber) {
        return this.video1Files[frameNumber];
    }

    public void setPrimaryVideoFolder(File file) {
        this.video1Folder = file;
    }

    public File getPrimaryVideoFolder() {
        return this.video1Folder;
    }

    public void setSecondaryVideoFolder(File file) {
        this.video2Folder = file;
    }

    public File getSecondaryVideoFolder() {
        return this.video2Folder;
    }

    public void setSecondaryVideoFile(File[] file) {
        this.video2Files = file;
    }

    public File[] getSecondaryVideoFiles() {
        return this.video2Files;
    }

    public File getSecondaryVideoFile(int frameNumber) {
        return this.video2Files[frameNumber];
    }
}
