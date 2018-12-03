package com.sungpi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveVideoPlayer {
    InteractiveVideo interactiveVideo;
    int cFrame = 1;

    InteractiveVideoPlayer(InteractiveVideo interactiveVideo) {
        this.interactiveVideo = interactiveVideo;
    }

    public void play() {

        JFrame frame = new JFrame();
        JLayeredPane layeredPane = new JLayeredPane();
        frame.setLayeredPane(layeredPane);
        GridBagLayout gLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gLayout);
        BufferedImage bufferedImage = new BufferedImage(InteractiveVideoReader.WIDTH, InteractiveVideoReader.HEIGHT, BufferedImage.TYPE_INT_RGB);
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JLabel videoLabel = new JLabel(imageIcon);
//        videoLabel.setLayout(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        frame.getContentPane().add(videoLabel, c);

        frame.pack();
        frame.setVisible(true);

        float fpsTime = 1000000000/30;
        long lastUpdate = System.nanoTime();
        boolean flag = false;

        while(cFrame < 9001) {
            if (flag == false) {
                bufferedImage = new BufferedImage(InteractiveVideoReader.WIDTH, InteractiveVideoReader.HEIGHT, BufferedImage.TYPE_INT_RGB);
                for(int y = 0; y < InteractiveVideoReader.HEIGHT; y++){
                    for(int x = 0; x < InteractiveVideoReader.WIDTH; x++){
                        bufferedImage.setRGB(x,y, interactiveVideo.getVideo().get(cFrame)[y][x]);
                    }
                }
                flag = true;
            }

            if(fpsTime < System.nanoTime() - lastUpdate) {
                flag = false;
                imageIcon = new ImageIcon(bufferedImage);
                imageIcon.getImage().flush();
                videoLabel.setIcon(imageIcon);
                cFrame++;

                lastUpdate = System.nanoTime();
            }
        }
    }
}
