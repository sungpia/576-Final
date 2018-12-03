package com.sungpi.Windows;

import com.sungpi.Caches.CaptureToolCache;
import com.sungpi.Frames.CaptureFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CaptureWindow extends WindowAdapter {
    private int maxY = 500;
    private int maxX = 500;

    private static JButton defaultButton = null;

    protected final static String CREATE_WINDOW = "new_win";

    public CaptureWindow() {
//        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
//        maxX = screensize.width - 50;
//        maxY = screensize.height - 50;
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
            CaptureToolCache.getInstance().setCaptureWindow(this);

        } catch (Exception e) { }
    }

    public void showCaptureWindow() {

        CaptureFrame captureFrame = new CaptureFrame();
        captureFrame.addComponentListener(new CaptureWindowListener());
//        captureFrame.setUndecorated(false);

        captureFrame.setSize(new Dimension(160, 160));
        captureFrame.setAlwaysOnTop(true);
        captureFrame.setOpacity(0.5f);
        captureFrame.setVisible(true);
        CaptureToolCache.getInstance().setCaptureFrame(captureFrame);
    }
}
