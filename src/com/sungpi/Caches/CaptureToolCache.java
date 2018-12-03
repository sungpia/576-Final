package com.sungpi.Caches;

import com.sungpi.Frames.CaptureFrame;
import com.sungpi.Windows.CaptureWindow;

import javax.swing.*;

public class CaptureToolCache {
    private static final CaptureToolCache instance = new CaptureToolCache();
    private CaptureWindow captureWindow;

    public CaptureWindow getCaptureWindow() {
        return captureWindow;
    }

    public void setCaptureWindow(CaptureWindow captureWindow) {
        this.captureWindow = captureWindow;
    }

    private CaptureFrame captureFrame;
    private JLabel captureLabel;

    private int captureFrameX;
    private int captureFrameY;

    private int captureLabelX;
    private int captureLabelY;

    public int getCaptureFrameX() {
        return captureFrameX;
    }

    public void setCaptureFrameX(int captureFrameX) {
        this.captureFrameX = captureFrameX;
    }

    public int getCaptureFrameY() {
        return captureFrameY;
    }

    public void setCaptureFrameY(int captureFrameY) {
        this.captureFrameY = captureFrameY;
    }

    public int getCaptureLabelX() {
        return captureLabelX;
    }

    public void setCaptureLabelX(int captureLabelX) {
        this.captureLabelX = captureLabelX;
    }

    public int getCaptureLabelY() {
        return captureLabelY;
    }

    public void setCaptureLabelY(int captureLabelY) {
        this.captureLabelY = captureLabelY;
    }

    public JLabel getCaptureLabel() {
        return captureLabel;
    }

    public void setCaptureLabel(JLabel captureLabel) {
        this.captureLabel = captureLabel;
    }

    private int windowHeight;
    private int windowWidth;
    private boolean isWindowActivated = false;
    private int frameHeight = 100;
    private int frameWidth = 100;

    public CaptureFrame getCaptureFrame() {
        return captureFrame;
    }

    public void setCaptureFrame(CaptureFrame captureFrame) {
        this.captureFrame = captureFrame;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
        this.frameHeight = (int) Math.round((double) this.windowHeight / 16 * 10);
        System.out.println("new frame height: " + this.frameHeight);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
        this.frameWidth = (int) Math.round((double) this.windowWidth / 16 * 10);
        System.out.println("new frame width: " + this.frameWidth);
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    private CaptureToolCache() {}

    public static CaptureToolCache getInstance() {
        return instance;
    }

    public boolean isWindowActivated() {
        return isWindowActivated;
    }

    public void setWindowActivated(boolean windowActivated) {
        isWindowActivated = windowActivated;
    }
}
