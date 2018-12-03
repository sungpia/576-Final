package com.sungpi.Frames;

import javax.swing.JInternalFrame;

import java.awt.event.*;
import java.awt.*;

/* Used by InternalFrameDemo.java. */
public class LoggerFrame extends JInternalFrame {

    public LoggerFrame() {
        super("Logger",
                false, //resizable
                false, //closable
                false, //maximizable
                false);//iconifiable

        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(900 ,200);

        //Set the window's location.
        setLocation(0, 450);

//        JComponent newContentPane = new MouseEventDemo();
//        newContentPane.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(newContentPane);

    }
}