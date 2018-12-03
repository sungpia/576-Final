package com.sungpi.Caches;

import com.sungpi.Frames.AuthoringToolFrame;
import com.sungpi.Frames.MainFrame;

import javax.swing.*;

public class PrimaryVideoPositionCache {
    private static final PrimaryVideoPositionCache instance = new PrimaryVideoPositionCache();

    private static int MENU_ADJUSTMENT = 22;
    private static int X_ADJUSTMENT = 54;
    private static int Y_ADJUSTMENT = 26;

    private PrimaryVideoPositionCache() {}
    public static PrimaryVideoPositionCache getInstance() { return instance; }

    private AuthoringToolFrame authoringToolFrame;
    private MainFrame mainFrame;
    private JLabel primaryVideo;

    public JLabel getPrimaryVideo() {
        return primaryVideo;
    }

    public void setPrimaryVideo(JLabel primaryVideo) {
        this.primaryVideo = primaryVideo;
    }

    public int getAuthoringToolFrameX() {
        return authoringToolFrameX;
    }

    public int getAuthoringToolFrameY() {
        return authoringToolFrameY;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public AuthoringToolFrame getAuthoringToolFrame() {
        return authoringToolFrame;
    }

    public void setAuthoringToolFrame(AuthoringToolFrame authoringToolFrame) {
        this.authoringToolFrame = authoringToolFrame;
    }

    private int mainFrameX;
    private int mainFrameY;

    public int getMainFrameX() {
        return mainFrameX;
    }

    public int getMainFrameY() {
        return mainFrameY;
    }

    private int authoringToolFrameX;
    private int authoringToolFrameY;

    private int primaryVideoX;
    private int primaryVideoY;

    public void setPrimaryVideoX(int primaryVideoX) {
        this.primaryVideoX = primaryVideoX;
    }

    public void setPrimaryVideoY(int primaryVideoY) {
        this.primaryVideoY = primaryVideoY;
    }

    public void setMainFrameX(int mainFrameX) {
        this.mainFrameX = mainFrameX;
    }

    public void setMainFrameY(int mainFrameY) {
        this.mainFrameY = mainFrameY;
    }

    public void setAuthoringToolFrameX(int authoringToolFrameX) {
        this.authoringToolFrameX = authoringToolFrameX;
    }

    public void setAuthoringToolFrameY(int authoringToolFrameY) {
        this.authoringToolFrameY = authoringToolFrameY;
    }

    public int getPrimaryVideoX() {
        return this.primaryVideoX;
    }

    public int getPrimaryVideoY() {
        return this.primaryVideoY;
    }

    public int getAdjustedX() {
        return this.mainFrameX + this.primaryVideoX + this.authoringToolFrameX + X_ADJUSTMENT;
    }

    public int getAdjustedY() {
        return this.mainFrameY + this.primaryVideoY + this.authoringToolFrameY + Y_ADJUSTMENT + MENU_ADJUSTMENT * 2;
    }
}
