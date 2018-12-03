package com.sungpi.Frames;

import com.sungpi.Caches.AuthoringToolCache;
import com.sungpi.Caches.PrimaryVideoPositionCache;
import com.sungpi.Frames.Listeners.AuthoringToolListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AuthoringToolFrame extends JInternalFrame implements ActionListener, WindowListener, ChangeListener {
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    JLabel primaryVideo;
    JLabel secondaryVideo;
    JSlider showFrame1 = new JSlider(JSlider.HORIZONTAL, 1, 9000, 1);
    JSlider showFrame2 = new JSlider(JSlider.HORIZONTAL, 1, 9000, 1);
    int prevFrame1 = 1;
    int prevFrame2 = 1;
    JLabel slider1Label = new JLabel("Frame: " + showFrame1.getValue(), JLabel.CENTER);
    JLabel slider2Label = new JLabel("Frame: " + showFrame2.getValue(), JLabel.CENTER);
    ImageIcon imageIcon;
    GridBagConstraints c = new GridBagConstraints();

    public AuthoringToolFrame() {
        super("Authoring Tool",
                false, //resizable
                false, //closable
                false, //maximizable
                false);//iconifiable


        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(800,450);

        //Set the window's location.
        setLocation(0, 0);

        GridBagLayout gLayout = new GridBagLayout();
        getContentPane().setLayout(gLayout);

        JLabel lbText1 = new JLabel("Primary Video");
        lbText1.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lbText2 = new JLabel("Linked Video");
        lbText2.setHorizontalAlignment(SwingConstants.CENTER);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        getContentPane().add(lbText1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        getContentPane().add(lbText2, c);

        primaryVideo = new JLabel(new ImageIcon(new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB)));
        secondaryVideo = new JLabel(new ImageIcon(new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB)));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        getContentPane().add(primaryVideo, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        getContentPane().add(secondaryVideo, c);

//        System.out.println(primaryVideo.get());
        c.gridx = 0;
        c.gridy = 3;
        showFrame1.addChangeListener(this);
        getContentPane().add(showFrame1, c);

//        JLabel slider2Label = new JLabel("Frame", JLabel.CENTER);
        c.gridx = 1;
        c.gridy = 3;
        showFrame2.addChangeListener(this);
        getContentPane().add(showFrame2, c);

        c.gridx = 0;
        c.gridy = 2;
        getContentPane().add(slider1Label, c);

        c.gridx = 1;
        c.gridy = 2;
        getContentPane().add(slider2Label, c);

        PrimaryVideoPositionCache.getInstance().setAuthoringToolFrame(this);
        System.out.println("Primary Video pos : " + primaryVideo.getBounds());
        AuthoringToolListener authoringToolListener = new AuthoringToolListener();
        authoringToolListener.setPrimaryVideo(primaryVideo);
        authoringToolListener.setAuthoringToolFrame(this);
        authoringToolListener.setSecondaryVideo(secondaryVideo);


        addComponentListener(authoringToolListener);

        System.out.println(PrimaryVideoPositionCache.getInstance().getAuthoringToolFrameX());
        System.out.println(PrimaryVideoPositionCache.getInstance().getAuthoringToolFrameY());

        System.out.println(PrimaryVideoPositionCache.getInstance().getPrimaryVideoX());
        System.out.println(PrimaryVideoPositionCache.getInstance().getPrimaryVideoY());

        PrimaryVideoPositionCache.getInstance().setPrimaryVideo(primaryVideo);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.CENTER;




//        getContentPane().add(slider, c);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 2;
//        getContentPane().add(playButton,c);
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 2;
//        getContentPane().add(pauseButton,c);
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 0;
//        c.gridy = 2;
//        frame.getContentPane().add(stopButton,c);

//        SwingUtilities.convertPointToScreen(new Point(primaryVideo.getLocation()), primaryVideo);
//        primaryVideo.setVisible(true);
//        System.out.println("Primary Video pos : " + primaryVideo.getLocationOnScreen());

//        pack();
    }

    public void stateChanged(ChangeEvent e) {
////        JSlider source = (JSlider)e.getSource();
//        if (!source.getValueIsAdjusting()) {
//            int fps = (int)source.getValue();
//            if (fps == 0) {
//                if (!frozen) stopAnimation();
//            } else {
//                delay = 1000 / fps;
//                timer.setDelay(delay);
//                timer.setInitialDelay(delay * 10);
//                if (frozen) startAnimation();
//            }
//        }
        if (prevFrame1 != showFrame1.getValue()) {
            slider1Label.setText("Frame: " + showFrame1.getValue());
            prevFrame1 = showFrame1.getValue();
            File file = AuthoringToolCache.getInstance().getPrimaryVideoFile(prevFrame1-1);
            setFrame(file, 1);
            AuthoringToolCache.getInstance().setVideo1Frame(prevFrame1 - 1);
        }

        if (prevFrame2 != showFrame2.getValue()) {
            slider2Label.setText("Frame: " + showFrame2.getValue());
            prevFrame2 = showFrame2.getValue();
            File file = AuthoringToolCache.getInstance().getSecondaryVideoFile(prevFrame2-1);
            setFrame(file, 2);
            AuthoringToolCache.getInstance().setVideo2Frame(prevFrame2 - 1);
        }
    }

    public void setFrame(File file, int video){
        try {
            InputStream is = new FileInputStream(file);

            long len = file.length();
            byte[] bytes = new byte[388800];

            int offset = 0;
            int numRead = 0;

            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            BufferedImage bufferedImage = new BufferedImage(352, 288, BufferedImage.TYPE_INT_RGB);
            //read image
            int ind = 0;
            for(int y = 0; y < 288; y++){
                for(int x = 0; x < 352; x++){

                    byte r = bytes[ind];
                    byte g = bytes[ind + 288 * 352];
                    byte b = bytes[ind + 288 * 352 * 2];

                    int pix = 0xff000000 | (((r) & 0xff) << 16) | (((g) & 0xff) << 8) | ((b) & 0xff);
                    bufferedImage.setRGB(x,y,pix);
                    ind++;
                }
            }
            imageIcon = new ImageIcon(bufferedImage);
            imageIcon.getImage().flush();
            if(video == 1) {
                primaryVideo.setIcon(imageIcon);
            } else {
                secondaryVideo.setIcon(imageIcon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHyperLinkBox() {
        c.gridx = 0;
        c.gridy = 1;
//        ImageIcon hyperLinkIcon = new ImageIcon();
//        JLabel hyperLinkBox = new ImageIcon(hyperLinkIcon);
//        hyperLinkBox.setOpaque(false);
//        hyperLinkBox.setContentAreaFilled(false);
//        hyperLinkBox.setBorderPainted(true);

//        getContentPane().add(hyperLinkBox, c);
    }

    //get hyperlink (x, y)
    // post it on screen
    // save as temp data in array


    //Called when the Timer fires.
    public void actionPerformed(ActionEvent e) {
        //Advance the animation frame.
        System.out.println("action detected");
    }

    //TODO(sungwonc): refactor neatly.

    /** Add a listener for window events. */
    void addWindowListener(Window w) {
        w.addWindowListener(this);
    }

    //React to window events.
    public void windowIconified(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}