package com.sungpi.Caches;

import com.sungpi.Frames.MetaDataDisplayFrame;
import com.sungpi.MetaData;

import java.util.ArrayList;
import java.util.List;

public class MetaDataCache {
    private static final MetaDataCache instance = new MetaDataCache();
    private MetaDataDisplayFrame metaDataDisplayFrame;

    public void setThumnailActionList(List<MetaDataDisplayFrame.ThumbnailAction> thumnailActionList) {
        this.thumnailActionList = thumnailActionList;
    }

    private List<MetaDataDisplayFrame.ThumbnailAction> thumnailActionList = new ArrayList<>();
    public ArrayList<MetaData> getMetaDatas() {
        return metaDatas;
    }

    private ArrayList<MetaData> metaDatas;

    public MetaDataDisplayFrame getMetaDataDisplayFrame() {
        return metaDataDisplayFrame;
    }

    public void setMetaDataDisplayFrame(MetaDataDisplayFrame metaDataDisplayFrame) {
        this.metaDataDisplayFrame = metaDataDisplayFrame;
    }

    private MetaDataCache() {
        metaDatas = new ArrayList<>();
    }

    public static MetaDataCache getInstance() { return instance; }

    public void addMetaData(MetaData metaData) {
        metaDatas.add(metaData);
        this.metaDataDisplayFrame.generateThumbAction();
    }
}
