package com.sungpi.InteractiveVideoPlayerUtils;

import com.sungpi.Caches.InteractiveVideoPlayerCache;
import com.sungpi.Frames.Listeners.InteractiveVideoPlayerListener;
import com.sungpi.InteractiveVideoPlayer;
import com.sungpi.MetaButton;
import com.sungpi.MetaData.MetaData;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;

public class InteractiveVideoPlayerUtil {
    private static final InteractiveVideoPlayerUtil instance = new InteractiveVideoPlayerUtil();
    private InteractiveVideoPlayerUtil() {}

    public static InteractiveVideoPlayerUtil getInstance() { return instance; }

    public void readData() {
//        InteractiveVideoPlayerCache cache = InteractiveVideoPlayerCache.getInstance();
//
//        File currentFolder = cache.getInteractiveVideoFolder();
//        cache.getFileNames();
//        cache.Wav();

    }

    public void getWav() {}
    public void getFileNames() {}


    public TimerTask playAudio = new TimerTask() {
        @Override
        public void run() {
            boolean isPlaying = InteractiveVideoPlayerCache.getInstance().isPlaying();
            Clip clip = InteractiveVideoPlayerCache.getInstance().getClip();

            if(isPlaying == false){
                clip.stop();
            }
        }
    };

    public TimerTask grabFrame = new TimerTask()  {
        @Override
        public void run() {
            boolean isPlaying = InteractiveVideoPlayerCache.getInstance().isPlaying();
            int frameNum = InteractiveVideoPlayerCache.getInstance().getFrameNum();
            ArrayList<String> fileNames = InteractiveVideoPlayerCache.getInstance().getRgbFileNames();
            File currentFolder = InteractiveVideoPlayerCache.getInstance().getInteractiveVideoFolder();
            ArrayList<MetaData> metaArray = InteractiveVideoPlayerCache.getInstance().getMetaData();
            JTextField framenum = InteractiveVideoPlayerCache.getInstance().getFramenum();
            ArrayList<MetaButton> buttonList = InteractiveVideoPlayerCache.getInstance().getButtonList();
            JLabel image = InteractiveVideoPlayerCache.getInstance().getImage();
//            System.out.println(frameNum);
//            System.out.println(currentFolder);
//            System.out.println(fileNames.get(frameNum));

            if (isPlaying == true && frameNum < 9000) {
                for (int i =0; i<3; i++) {
                    BufferedImage img = getFrame(currentFolder, fileNames.get(frameNum));
                    ImageIcon imageIcon = new ImageIcon(img);
                    imageIcon.getImage().flush();
                    InteractiveVideoPlayerCache.getInstance().getImage().setIcon(imageIcon);
                    InteractiveVideoPlayerCache.getInstance().setFrameNum(frameNum+1);
                    framenum.setText(Integer.toString(frameNum));


                    // or this
                    for (int x=0; x<metaArray.size(); ++x) {
                        MetaData md = metaArray.get(x);
                        if (md.getLinkeeStartFrame() <= frameNum && md.getLinkeeEndFrame() >= frameNum) {
                            // display corresponding button for current meta Data.
                            // buttonList.get(x).display(); -> its very abstract.
                            buttonList.get(x).setVisible(true);
                        }else{
                            buttonList.get(x).setVisible(false);
                        }
                    }
                    if (i==1) {
                        try {
                            Thread.sleep(33);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (i ==2){
                        try {
                            Thread.sleep(34);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }


    };

    public void getFileNames(File folder){


//        File folder = new File("H:/AIFilm/AIFilm/AIFilmTwo");

        //Implementing FilenameFilter to retrieve only txt files

        FilenameFilter txtFileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name)
            {
                if(name.endsWith(".rgb"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };



        //Passing txtFileFilter to listFiles() method to retrieve only txt files

        File[] files = folder.listFiles(txtFileFilter);

        for (File file : files)
        {
            //System.out.println(file.getName());
            InteractiveVideoPlayerCache.getInstance().getRgbFileNames().add(file.getName());
        }


    }

    public void getWav(File folder) {


//        File folder = new File("H:/AIFilm/AIFilm/AIFilmTwo");

        //Implementing FilenameFilter to retrieve only txt files

        FilenameFilter wavFileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".wav")) {
                    return true;
                } else {
                    return false;
                }
            }
        };


        //Passing txtFileFilter to listFiles() method to retrieve only txt files

        File[] files = folder.listFiles(wavFileFilter);

        for (File file : files)
        {
            //System.out.println(file.getName());
            InteractiveVideoPlayerCache.getInstance().setWav(file);
        }
    }

    public BufferedImage getFrame(File folder, String theFileName){
        BufferedImage img = new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB);

        try {
            String pathname = folder.getAbsolutePath() + "\\" + theFileName;
//            System.out.println(pathname);
            File sourceimage = new File(pathname);
//            System.out.println(pathname);

            InputStream is = new FileInputStream(sourceimage);

            byte[] bytes = new byte[388800];

            int offset = 0;
            int numRead = 0;

            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            //read image
            int ind = 0;
            for(int y = 0; y < 288; y++){
                for(int x = 0; x < 352; x++){

                    byte r = bytes[ind];
                    byte g = bytes[ind + 288 * 352];
                    byte b = bytes[ind + 288 * 352 * 2];

                    int pix = 0xff000000 | (((r) & 0xff) << 16) | (((g) & 0xff) << 8) | ((b) & 0xff);
                    img.setRGB(x,y,pix);

                    ind++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public void setLinks() {
        ArrayList<MetaData> metaArray = InteractiveVideoPlayerCache.getInstance().getMetaData();
        JLayeredPane videoOverlay = InteractiveVideoPlayerCache.getInstance().getVideoOverlay();
        //Metadata Buttons
        ArrayList<MetaButton> buttonList = new ArrayList<>();
        for( int i=0; i<metaArray.size(); i++){
            MetaButton linkButton = new MetaButton(i);
            videoOverlay.add(linkButton, new Integer(2));
            linkButton.setOpaque(false);
            linkButton.setContentAreaFilled(false);
            linkButton.setLocation(metaArray.get(i).getDisplacementX(),metaArray.get(i).getDisplacementY());
            linkButton.setSize(metaArray.get(i).getDimensionX(),metaArray.get(i).getDimensionY());
            linkButton.setVisible(false);
            linkButton.setActionCommand("goto");
            linkButton.addActionListener(new InteractiveVideoPlayerListener());
            buttonList.add(linkButton);
        }
        InteractiveVideoPlayerCache.getInstance().setButtonList(buttonList);
    }
}

