package com.scofd.gentemplate.model;

import java.util.List;

public class OPGPackageArgs {
    /**
     * 组件数据列表
     */
    private List<OPGComponentData> componentDataList;
    /**
     * 生成目录的标题
     */
    private String catalogueTitle;
    /**
     * 生成目录时是否将内容组件的文件名作为目录项，默认为true
     */
    private boolean includeFileName;
    /**
     * 生成目录时是否将内容组件的大纲（如有）作为目录项，默认为true
     */
    private boolean includeFileOutline;
    /**
     * 将内容组件的目录内容作为目录项时，遍历访问的深度，默认为2
     */
    private int fileOutlineDepth;
    /**
     * 分隔页的标题
     */
    private String sectionTitle;
    /**
     * 分隔页与其后内容的衔接关系，可取值Next（下一页）、Odd（奇数页）、Even（偶数页），默认为Odd
     */
    private String sectionType;
    /**
     * 分隔页页码设置之页码显示格式
     */
    private String sectionPageNumSettingDisplayFormat;
    /**
     * 分隔页页码设置之页码起始编号
     */
    private int sectionPageNumSettingStartIndex;

    public OPGPackageArgs() {
        includeFileName = true;
        includeFileOutline = true;
        fileOutlineDepth = 2;
        sectionType = "Odd";
    }

    public List<OPGComponentData> getComponentDataList() {
        return componentDataList;
    }

    public void setComponentDataList(List<OPGComponentData> componentDataList) {
        this.componentDataList = componentDataList;
    }

    public String getCatalogueTitle() {
        return catalogueTitle;
    }

    public void setCatalogueTitle(String catalogueTitle) {
        this.catalogueTitle = catalogueTitle;
    }

    public boolean isIncludeFileName() {
        return includeFileName;
    }

    public void setIncludeFileName(boolean includeFileName) {
        this.includeFileName = includeFileName;
    }

    public boolean isIncludeFileOutline() {
        return includeFileOutline;
    }

    public void setIncludeFileOutline(boolean includeFileOutline) {
        this.includeFileOutline = includeFileOutline;
    }

    public int getFileOutlineDepth() {
        return fileOutlineDepth;
    }

    public void setFileOutlineDepth(int fileOutlineDepth) {
        this.fileOutlineDepth = fileOutlineDepth;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getSectionPageNumSettingDisplayFormat() {
        return sectionPageNumSettingDisplayFormat;
    }

    public void setSectionPageNumSettingDisplayFormat(String sectionPageNumSettingDisplayFormat) {
        this.sectionPageNumSettingDisplayFormat = sectionPageNumSettingDisplayFormat;
    }

    public int getSectionPageNumSettingStartIndex() {
        return sectionPageNumSettingStartIndex;
    }

    public void setSectionPageNumSettingStartIndex(int sectionPageNumSettingStartIndex) {
        this.sectionPageNumSettingStartIndex = sectionPageNumSettingStartIndex;
    }
}
