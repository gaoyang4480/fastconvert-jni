package com.scofd.gentemplate.model;

import java.util.List;

public class OPGComponentData {
    private byte[] componentData;
    private String componentFilePath;
    private String title;
    private String format;
    private List<Attachments> attachs;

    public byte[] getComponentData() {
        return componentData;
    }

    public void setComponentData(byte[] componentData) {
        this.componentData = componentData;
    }

    public String getComponentFilePath() {
        return componentFilePath;
    }

    public void setComponentFilePath(String componentFilePath) {
        this.componentFilePath = componentFilePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<Attachments> getAttachs() {
        return attachs;
    }

    public void setAttachs(List<Attachments> attachs) {
        this.attachs = attachs;
    }
}
