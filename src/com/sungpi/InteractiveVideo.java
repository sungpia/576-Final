package com.sungpi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.HashMap;

public class InteractiveVideo {
    HashMap<Integer, int[][]> video;
    String name;
    MetaData metadata;

    InteractiveVideo(String name, HashMap<Integer, int[][]> video) {
        this.name = name;
        this.video = video;

        MetaData mockMetaData = new MetaData("AIFilm/AIFilmOne",
                5, 200, 20, 20, 100, 100,
                "AIFilm/AIFilmTwo", 1000);
        this.metadata = mockMetaData;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<Integer, int[][]> getVideo() {
        return this.video;
    }
}
