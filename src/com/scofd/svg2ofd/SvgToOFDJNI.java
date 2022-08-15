package com.scofd.svg2ofd;

public class SvgToOFDJNI {
    public native String convertSvgFileToAbbreviatedData(String svgFilePath, int svgWidth, int svgHeight);

    public native String convertSvgDataToAbbreviatedData(byte[] svgData, int svgWidth, int svgHeight);

    public static void main(String[] args) {
        SvgToOFDJNI svgToOFDJNI = new SvgToOFDJNI();
        System.loadLibrary("yh_svg2vectorsymbol");
        String svgAbbreviatedDataStr = svgToOFDJNI.convertSvgFileToAbbreviatedData("C:\\Users\\gaoyang\\Downloads\\svg\\youhong.svg", 64, 64);
        if (svgAbbreviatedDataStr != null) {
            System.out.println(svgAbbreviatedDataStr);
        }
    }

}
