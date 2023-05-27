package com.scofd.gentemplate.model;

public class MultiMediaData {
    private byte[] File;
    private String Format;
    private String FilePath;

    public MultiMediaData() {

    }

    public MultiMediaData(String filePath, String format) {
        this.FilePath = filePath;
        this.Format = format;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        this.Format = format;
    }

    public byte[] getFile() {
        return File;
    }

    public void setFile(byte[] file) {
        this.File = file;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

}
