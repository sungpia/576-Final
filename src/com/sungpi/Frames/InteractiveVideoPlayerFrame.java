package com.sungpi.Frames;

import com.sungpi.InteractiveVideoPlayerUtils.InteractiveVideoPlayerOption;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/* Used by InternalFrameDemo.java. */
public class InteractiveVideoPlayerFrame extends JInternalFrame {
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;

    BufferedImage bufferedImage;
    InteractiveVideoPlayerOption interactiveVideoPlayerOption = InteractiveVideoPlayerOption.getInstance();
    ImageIcon imageIcon;
    JLabel videoLabel;
    boolean flag = false;
    int cFrame = 1;


    float fpsTime = 1000000000/30;
    long lastUpdate = System.nanoTime();

    public InteractiveVideoPlayerFrame() {
        super("Interactive Video Player",
                false, //resizable
                false, //closable
                false, //maximizable
                false);//iconifiable

        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(450,400);

        //Set the window's location.
        setLocation(900, 450);

        GridBagLayout gridBagLayout = new GridBagLayout();
        getContentPane().setLayout(gridBagLayout);
        bufferedImage = new BufferedImage(interactiveVideoPlayerOption.WIDTH,
                interactiveVideoPlayerOption.HEIGHT, BufferedImage.TYPE_INT_RGB
                );
        imageIcon = new ImageIcon(bufferedImage);
        videoLabel = new JLabel(imageIcon);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(videoLabel, gridBagConstraints);
    }

    // TODO(sungwonc): Make sure re-initialize variable for player for each import.
    public void play() {


        while(cFrame < 9001) {
            if (flag == false) {
                bufferedImage = new BufferedImage(interactiveVideoPlayerOption.WIDTH, interactiveVideoPlayerOption.HEIGHT, BufferedImage.TYPE_INT_RGB);
                for(int y = 0; y < interactiveVideoPlayerOption.HEIGHT; y++){
                    for(int x = 0; x < interactiveVideoPlayerOption.WIDTH; x++){
                        bufferedImage.setRGB(x,y, interactiveVideoPlayerOption.getInteractiveVideo().getVideo().get(cFrame)[y][x]);
                    }
                }
                flag = true;
            }

            if(fpsTime < System.nanoTime() - lastUpdate) {
                System.out.println("playing frame # " + cFrame);
                flag = false;
                imageIcon = new ImageIcon(bufferedImage);
                imageIcon.getImage().flush();
                videoLabel.setIcon(imageIcon);
                cFrame++;

                lastUpdate = System.nanoTime();

                break;
            }

        }
    }
}