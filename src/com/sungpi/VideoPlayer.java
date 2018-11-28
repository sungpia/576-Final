package com.sungpi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoPlayer {
    Video video;
    int cFrame = 1;

    VideoPlayer(Video video) {
        this.video = video;
    }

    public void play() {

        JFrame frame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gLayout);
        BufferedImage bufferedImage = new BufferedImage(RgbsReader.WIDTH, RgbsReader.HEIGHT, BufferedImage.TYPE_INT_RGB);
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JLabel videoLabel = new JLabel(imageIcon);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.getContentPane().add(videoLabel, c);
        frame.pack();
        frame.setVisible(true);


        while(cFrame < 9001) {
            System.out.println("displaying: " + cFrame);


            bufferedImage = new BufferedImage(RgbsReader.WIDTH, RgbsReader.HEIGHT, BufferedImage.TYPE_INT_RGB);

            for(int y = 0; y < RgbsReader.HEIGHT; y++){
                for(int x = 0; x < RgbsReader.WIDTH; x++){
                    bufferedImage.setRGB(x,y,video.getVideo().get(cFrame)[y][x]);
                }
            }
            imageIcon = new ImageIcon(bufferedImage);
            imageIcon.getImage().flush();
            videoLabel.setIcon(imageIcon);

            //here
//            videoLabel = new JLabel(new ImageIcon(bufferedImage));
//            imageIcon = new ImageIcon(bufferedImage);
//            imageIcon.getImage().flush();
//            videoLabel.setIcon(imageIcon);
//            videoLabel.revalidate();
//            videoLabel.repaint();
            frame.pack();
            cFrame++;
        }
    }

}
