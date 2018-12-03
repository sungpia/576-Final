package com.sungpi.Windows;

import com.sungpi.Caches.CaptureToolCache;
import com.sungpi.Frames.CaptureFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

public class CaptureWindowListener implements ComponentListener, AWTEventListener {

    static int PADDING = 5;
    static int TITLE_PADDING = 23;
    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {
        CaptureToolCache captureToolCache = CaptureToolCache.getInstance();
        captureToolCache.setCaptureFrameX((int) e.getComponent().getBounds().getLocation().getX());
        captureToolCache.setCaptureFrameY((int) e.getComponent().getBounds().getLocation().getY());
        int x = (int) e.getComponent().getBounds().getLocation().getX() +
                (int) CaptureToolCache.getInstance().getCaptureLabel().getBounds().getLocation().getX() +
                PADDING;
        int y = (int) e.getComponent().getBounds().getLocation().getY() +
                (int) CaptureToolCache.getInstance().getCaptureLabel().getBounds().getLocation().getY() +
                TITLE_PADDING +
                PADDING;
        captureToolCache.setCaptureLabelX(x);
        captureToolCache.setCaptureLabelY(y);
        System.out.println("Capture Tool From (" + x + ", " + y +") " +
                "to (" + (x + captureToolCache.getFrameWidth() - 1) +
                ", " + (y + captureToolCache.getFrameHeight() - 1) + ")");
    }
    public void componentShown(ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        System.out.println("new Height: " + e.getComponent().getHeight());
        System.out.println("new Width: " + e.getComponent().getWidth());

        CaptureToolCache.getInstance().setWindowHeight(e.getComponent().getHeight());
        CaptureToolCache.getInstance().setWindowWidth(e.getComponent().getWidth());
        CaptureToolCache.getInstance().getCaptureFrame().redraw();

        CaptureToolCache captureToolCache = CaptureToolCache.getInstance();
        captureToolCache.setCaptureFrameX((int) e.getComponent().getBounds().getLocation().getX());
        captureToolCache.setCaptureFrameY((int) e.getComponent().getBounds().getLocation().getY());
        int x = (int) e.getComponent().getBounds().getLocation().getX() +
                (int) CaptureToolCache.getInstance().getCaptureLabel().getBounds().getLocation().getX() +
                PADDING;
        int y = (int) e.getComponent().getBounds().getLocation().getY() +
                (int) CaptureToolCache.getInstance().getCaptureLabel().getBounds().getLocation().getY() +
                TITLE_PADDING +
                PADDING;
        captureToolCache.setCaptureLabelX(x);
        captureToolCache.setCaptureLabelY(y);
    }


    public void eventDispatched(AWTEvent event) {
        switch (event.getID()){
            case WindowEvent.WINDOW_OPENED:
                break;
            case WindowEvent.WINDOW_CLOSED:
                CaptureToolCache.getInstance().setWindowActivated(false);
                break;
        }
    }
}