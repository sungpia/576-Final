package com.sungpi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;

public class RgbsReader {

    String filePath;
    static final int WIDTH = 352;
    static final int HEIGHT = 288;
    HashMap<Integer, int[][]> rgbVals = new HashMap<>();

    RgbsReader() {
    }

    private void readRgbs() {
//        try {
//            File file = new File(filePath);
//            InputStream inputStream = new FileInputStream(file);
//
//            originalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//
//            byte[] bytes = new byte[WIDTH * HEIGHT * 3];
//            int offset = 0;
//            int numRead = 0;
//            while (offset < bytes.length && (numRead=inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
//                offset += numRead;
//            }
//
//            int ind = 0;
//            for(int y = 0; y < HEIGHT; y++){
//                for(int x = 0; x < WIDTH; x++){
//                    byte r = bytes[ind];
//                    byte g;
//                    byte b;
//                    if(!isGreyScale) {
//                        g = bytes[ind + HEIGHT * WIDTH];
//                        b = bytes[ind + HEIGHT * WIDTH * 2];
//                    } else {
//                        g = r;
//                        b = r;
//                    }
//                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
//                    originalImage.setRGB(x,y,pix);
//
//                    // Caching RGB/Greyscale with Class Color on memory.
//                    int rgb = originalImage.getRGB(x,y);
//                    if(isGreyScale) colors[y][x] = new Color(rgb, true);
//                    else colors[y][x] = new Color(rgb, false);
//
//                    ind++;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void createHashMapImageBuffer(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            String filename = file.getName();
            int lastindex = filename.lastIndexOf('.');
            String index = filename.substring(lastindex-4, lastindex);
//            System.out.println(index);
            BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            byte[] bytes = new byte[WIDTH * HEIGHT * 3];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead=inputStream.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            int[][] rgbVal = new int[HEIGHT][WIDTH];
            int ind = 0;
            for(int y = 0; y < HEIGHT; y++){
                for(int x = 0; x < WIDTH; x++){
                    byte r = bytes[ind];
                    byte g;
                    byte b;
                    r = bytes[ind];
                    g = bytes[ind + HEIGHT * WIDTH];
                    b = bytes[ind + HEIGHT * WIDTH * 2];

                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    bufferedImage.setRGB(x,y,pix);
                    rgbVal[y][x] = pix;
                    ind++;
                }
            }
            rgbVals.put(Integer.parseInt(index), rgbVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        System.out.println(Integer.parseInt("0001"));
        // write your code here
        File folder = new File("./AIFilm/AIFilmOne");
        File[] rgbFiles = folder.listFiles(new RgbFileFilter());
        Arrays.sort(rgbFiles);
        for (File file : rgbFiles) {
            System.out.println(file.getName());
        }

        File[] wavFiles = folder.listFiles(new WavFileFilter());
        File wavFile = wavFiles[0];
        System.out.println(wavFile.getName());

        RgbsReader rgbsReader = new RgbsReader();

        for (File file : rgbFiles) {
            rgbsReader.createHashMapImageBuffer(file);
        }
        Video video = new Video(wavFile.getName().substring(wavFile.getName().lastIndexOf('.')), rgbsReader.rgbVals);
        VideoPlayer videoPlayer = new VideoPlayer(video);
        videoPlayer.play();


        MetaData mockMetaData = new MetaData("AIFilm/AIFilmOne",
                5, 200, 20, 20, 100, 100,
                "AIFilm/AIFilmTwo", 1000);

    }
}
