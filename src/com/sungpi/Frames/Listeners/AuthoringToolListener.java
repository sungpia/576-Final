package com.sungpi.Frames.Listeners;

import com.sungpi.Caches.AuthoringToolCache;
import com.sungpi.Caches.CaptureToolCache;
import com.sungpi.Caches.PrimaryVideoPositionCache;
import com.sungpi.Frames.AuthoringToolFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class AuthoringToolListener implements ComponentListener {

    JLabel primaryVideo;
    JLabel secondaryVideo;
    AuthoringToolFrame authoringToolFrame;

    public void setPrimaryVideo(JLabel primaryVideo) {
        this.primaryVideo = primaryVideo;
    }
    public void setSecondaryVideo(JLabel secondaryVideo) { this.secondaryVideo = secondaryVideo; }

    public void setAuthoringToolFrame(AuthoringToolFrame authoringToolFrame) {
        this.authoringToolFrame = authoringToolFrame;
    }
    public void componentHidden(ComponentEvent e) {}
    public void componentMoved(ComponentEvent e) {
        PrimaryVideoPositionCache primaryVideoPositionCache = PrimaryVideoPositionCache.getInstance();

        primaryVideoPositionCache.setAuthoringToolFrameX(
                (int) e.getComponent().getBounds().getLocation().getX());
        primaryVideoPositionCache.setAuthoringToolFrameY(
                (int) e.getComponent().getBounds().getLocation().getY());

        primaryVideoPositionCache.setPrimaryVideoX(
                (int) primaryVideo.getBounds().getLocation().getX()
        );
        primaryVideoPositionCache.setPrimaryVideoY(
                (int) primaryVideo.getBounds().getLocation().getY()
        );

        System.out.println("AuthoringTool x(bound) : " + e.getComponent().getBounds().getLocation().getX());
        System.out.println("AuthoringTool y(bound) : " + e.getComponent().getBounds().getLocation().getY());
        System.out.println("Primary Video x(bound) : " + primaryVideo.getBounds().getLocation().getX());
        System.out.println("Primary Video y(bound) : " + primaryVideo.getBounds().getLocation().getY());
        System.out.println(primaryVideoPositionCache.getAdjustedX());
        System.out.println(primaryVideoPositionCache.getAdjustedY());

    }
    public void componentShown(ComponentEvent e) {}

    public void componentResized(ComponentEvent e) {
        PrimaryVideoPositionCache primaryVideoPositionCache = PrimaryVideoPositionCache.getInstance();

        primaryVideoPositionCache.setAuthoringToolFrameX(
                (int) e.getComponent().getBounds().getLocation().getX());
        primaryVideoPositionCache.setAuthoringToolFrameY(
                (int) e.getComponent().getBounds().getLocation().getY());

        primaryVideoPositionCache.setPrimaryVideoX(
                (int) primaryVideo.getBounds().getLocation().getX()
        );
        primaryVideoPositionCache.setPrimaryVideoY(
                (int) primaryVideo.getBounds().getLocation().getY()
        );

        System.out.println("AuthoringTool Height: " + e.getComponent().getHeight());
        System.out.println("AuthoringTool Width: " + e.getComponent().getWidth());
    }
}
