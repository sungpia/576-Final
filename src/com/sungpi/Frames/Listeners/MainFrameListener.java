package com.sungpi.Frames.Listeners;

import com.sungpi.Caches.AuthoringToolCache;
import com.sungpi.Caches.PrimaryVideoPositionCache;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrameListener implements ComponentListener {
    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {
        PrimaryVideoPositionCache.getInstance().setMainFrameX((int) e.getComponent().getBounds().getLocation().getX());
        PrimaryVideoPositionCache.getInstance().setMainFrameY((int) e.getComponent().getBounds().getLocation().getY());
        System.out.println("MainFrame x: " + e.getComponent().getBounds().getLocation().getX());
        System.out.println("MainFrame y: " + e.getComponent().getBounds().getLocation().getY());
    }

    public void componentShown(ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        PrimaryVideoPositionCache.getInstance().setMainFrameX((int) e.getComponent().getBounds().getLocation().getX());
        PrimaryVideoPositionCache.getInstance().setMainFrameY((int) e.getComponent().getBounds().getLocation().getY());

        System.out.println("MainFrame Height: " + e.getComponent().getHeight());
        System.out.println("MainFrame Width: " + e.getComponent().getWidth());
    }
}
