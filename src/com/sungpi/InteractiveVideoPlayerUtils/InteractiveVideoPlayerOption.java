package com.sungpi.InteractiveVideoPlayerUtils;

import com.sungpi.InteractiveVideo;
import com.sungpi.InteractiveVideoReader;

import java.io.File;

// TODO(sungwonc): This is singleton design pattern, ask me if confused.
public class InteractiveVideoPlayerOption {

    public static final int WIDTH = 352;
    public static final int HEIGHT = 288;
    private static final InteractiveVideoPlayerOption instance = new InteractiveVideoPlayerOption();
    private InteractiveVideo interactiveVideo;
    private File currentFile;
    private InteractiveVideoPlayerOption() {}

    public static InteractiveVideoPlayerOption getInstance() {
        return instance;
    }

    public void setInteractiveVideoFile(File file) {
        this.currentFile = file;
        InteractiveVideoReader interactiveVideoReader = new InteractiveVideoReader();
        interactiveVideoReader.readFileAndConstructHashMap();
        System.out.println("ready to play");
    }

    public File getInteractiveVideoFile() {
        return this.currentFile;
    }

    public InteractiveVideo getInteractiveVideo() {
        return this.interactiveVideo;
    }

    public void setInteractiveVideo(InteractiveVideo interactiveVideo) {
        this.interactiveVideo = interactiveVideo;
    }
}
// TODO(sungwonc); this is singleton pattern, allows one instance for class
//
//class MyClass {
//    private MyClass instance = null;
//    public static MyClass getInstance() {
//        if (instance == null) {
//            instance = new MyClass();
//        }
//        return instance;
//    }
//}