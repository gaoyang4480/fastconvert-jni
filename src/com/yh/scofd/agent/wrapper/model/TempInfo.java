package com.yh.scofd.agent.wrapper.model;

import java.io.File;

/**
 * @author ：2ming
 * @date ：Created in 2021/4/9 19:06
 * @modified By：2ming
 */
public class TempInfo {
    // 图片文件.
    private File file;
    // 图片二进制数据.
    private byte[] fileData;
    // 图片格式.
    private String imageFormat;
    // DPI.
    private int dpi = 96;
    // 图片宽度.
    private float width;
    // 图片高度.
    private float height;
    // 单位是否是毫米.
    private boolean mmUnit = false;
    // 透明度.
    private int alpha = 255;
    // 颜色值(rgb，以空格分隔，例如"255 0 0").
    private String colorVal;
    // 是否是底纹.
    private boolean isPattern = false;
    // 底纹图片宽度.
    private float patternWidth;
    // 底纹图片高度.
    private float patternHeight;
    // 底纹单元的翻转方式.
    private String reflectMethod = "Normal";

    public TempInfo() {

    }

    /**
     * 模板文件信息 单位为px
     *
     * @param file
     * @param dpi
     * @param width
     * @param height
     */
    public TempInfo(File file, int dpi, int width, int height) {
        this.file = file;
        this.dpi = dpi;
        this.width = width;
        this.height = height;
    }

    /**
     * 模板文件信息 单位为mm
     *
     * @param file
     * @param width
     * @param height
     */
    public TempInfo(File file, float width, float height) {
        this.file = file;
        this.width = width;
        this.height = height;
        this.mmUnit = true;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isMmUnit() {
        return mmUnit;
    }

    public void setMmUnit(boolean mmUnit) {
        this.mmUnit = mmUnit;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public String getColorVal() {
        return colorVal;
    }

    public void setColorVal(String colorVal) {
        this.colorVal = colorVal;
    }

    public boolean isPattern() {
        return isPattern;
    }

    public void setPattern(boolean pattern) {
        isPattern = pattern;
    }

    public float getPatternWidth() {
        return patternWidth;
    }

    public void setPatternWidth(float patternWidth) {
        this.patternWidth = patternWidth;
    }

    public float getPatternHeight() {
        return patternHeight;
    }

    public void setPatternHeight(float patternHeight) {
        this.patternHeight = patternHeight;
    }

    public String getReflectMethod() {
        return reflectMethod;
    }

    public void setReflectMethod(String reflectMethod) {
        this.reflectMethod = reflectMethod;
    }

}
