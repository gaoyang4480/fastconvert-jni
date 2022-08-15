package com.scofd.gentemplate.model;

/**
 * 附件数据
 *
 * @author gaoyang
 * @version 1.0
 * @date 2021/04/13 11:52
 * @see com.scofd.gentemplate.model
 */
public class Attachments {

    private String Name;
    private String Format;
    private String CreationDate;
    private String ModDate;
    private double Size;
    private boolean Visible;
    private String Usage;
    private byte[] File;
    private String FilePath;
    private String AttachRelativePath;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        this.Format = format;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        this.CreationDate = creationDate;
    }

    public String getModDate() {
        return ModDate;
    }

    public void setModDate(String modDate) {
        this.ModDate = modDate;
    }

    public double getSize() {
        return Size;
    }

    public void setSize(double size) {
        this.Size = size;
    }

    public boolean getVisible() {
        return Visible;
    }

    public void setVisible(boolean visible) {
        this.Visible = visible;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        this.Usage = usage;
    }

    public byte[] getFile() {
        return File;
    }

    public void setFile(byte[] file) {
        this.File = file;
    }

    public boolean isVisible() {
        return Visible;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getAttachRelativePath() {
        return AttachRelativePath;
    }

    public void setAttachRelativePath(String attachRelativePath) {
        AttachRelativePath = attachRelativePath;
    }
}
