package com.sungpi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;

public class Video {
    HashMap<Integer, int[][]> video;
    String name;


    Video(String name, HashMap<Integer, int[][]> video) {
        this.name = name;
        this.video = video;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Integer, int[][]> getVideo() {
        return this.video;
    }
}
