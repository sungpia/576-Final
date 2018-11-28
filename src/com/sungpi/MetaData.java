package com.sungpi;

public class MetaData {
    private String linkee;
    private int linkeeStartFrame;
    private int linkeeEndFrame;
    private int displacementX;
    private int displacementY;
    private int dimensionX;
    private int dimensionY;
    private String linked;
    private int linkedStartFrame;

    public MetaData(String linkee, int linkeeStartFrame, int linkeeEndFrame, int displacementX, int displacementY,
                    int dimensionX, int dimensionY, String linked, int linkedStartFrame) {
        this.linkee = linkee;
        this.linkeeStartFrame = linkeeStartFrame;
        this.linkeeEndFrame = linkeeEndFrame;
        this.displacementX = displacementX;
        this.displacementY = displacementY;
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.linked = linked;
        this.linkedStartFrame = linkedStartFrame;
    }

    public int getLinkeeStartFrame() {
        return linkeeStartFrame;
    }

    public void setLinkeeStartFrame(int linkeeStartFrame) {
        this.linkeeStartFrame = linkeeStartFrame;
    }

    public int getLinkeeEndFrame() {
        return linkeeEndFrame;
    }

    public void setLinkeeEndFrame(int linkeeEndFrame) {
        this.linkeeEndFrame = linkeeEndFrame;
    }

    public int getDisplacementX() {
        return displacementX;
    }

    public void setDisplacementX(int displacementX) {
        this.displacementX = displacementX;
    }

    public int getDisplacementY() {
        return displacementY;
    }

    public void setDisplacementY(int displacementY) {
        this.displacementY = displacementY;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public void setDimensionX(int dimensionX) {
        this.dimensionX = dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public void setDimensionY(int dimensionY) {
        this.dimensionY = dimensionY;
    }

    public String getLinked() {
        return linked;
    }

    public void setLinked(String linked) {
        this.linked = linked;
    }

    public int getLinkedStartFrame() {
        return linkedStartFrame;
    }

    public void setLinkedStartFrame(int linkedStartFrame) {
        this.linkedStartFrame = linkedStartFrame;
    }

    public String getLinkee() {
        return linkee;
    }

    public void setLinkee(String linkee) {
        this.linkee = linkee;
    }
}
