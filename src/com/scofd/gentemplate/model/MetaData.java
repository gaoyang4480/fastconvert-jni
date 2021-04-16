package com.scofd.gentemplate.model;

/**
 * 元数据
 *
 * @author gaoyang
 * @version 1.0
 * @date 2021/04/13 11:52
 * @see com.scofd.gentemplate.model
 */
public class MetaData {

    private String Name;
    private String Value;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }

}
