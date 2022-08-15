package com.yh.scofd.ofdsemanteme;

import java.io.*;
import java.nio.file.Paths;

public class OFDSemantemeJNI {
    public native boolean initialize(String key, String configData);

    public native void destroy();

    public native String getOFDSemantemeResult(byte[] srcOFDData, byte[] scriptData);

    public native String getOFDSemantemeResultByFilePath(String srcOFDFilePath, String scriptFilePath);

    public native OFDSemantemeResult getOFDSemantemeResultOFD(byte[] srcOFDData, byte[] scriptData);

    public native OFDSemantemeResult getOFDSemantemeResultOFDByFilePath(String srcOFDFilePath, String scriptFilePath);

    public static void main(String[] args) throws IOException {
        System.loadLibrary("zlib");
        System.loadLibrary("icuuc");
        System.loadLibrary("icui18n");
        System.loadLibrary("v8_libbase");
        System.loadLibrary("v8_libplatform");
        System.loadLibrary("v8");
        System.loadLibrary("expat");
        System.loadLibrary("xerces-c_3_2");
        System.loadLibrary("proj");
        System.loadLibrary("geos_c");
        System.loadLibrary("geos");
        System.loadLibrary("gdal201");
        System.loadLibrary("ofdsemanteme");

        OFDSemantemeJNI ofdSemantemeJNI = new OFDSemantemeJNI();
        byte[] configData = getFileByteArray(Paths.get(args[0]).toFile());
        String configDataStr = byteToString(configData);
        if (!ofdSemantemeJNI.initialize("", configDataStr)) {
            System.err.println("init error");
            return;
        }
        byte[] srcOFDData = getFileByteArray(Paths.get(args[1]).toFile());
        byte[] scriptData = getFileByteArray(Paths.get(args[2]).toFile());
        String resultStr = new String("");
        //resultStr = ofdSemantemeJNI.getOFDSemantemeResult(srcOFDData, scriptData);
        //resultStr = ofdSemantemeJNI.getOFDSemantemeResultByFilePath(args[1], args[2]);
        OFDSemantemeResult ofdSemantemeResult = ofdSemantemeJNI.getOFDSemantemeResultOFD(srcOFDData, scriptData);
        if (ofdSemantemeResult != null) {
            OutputStream resultOutStream = new FileOutputStream("./debug.ofd");
            resultOutStream.write(ofdSemantemeResult.ofdData);
            resultOutStream.close();
            System.out.println("write result ofd successfully ");

            resultStr = ofdSemantemeResult.resultStr;
        }

        if (resultStr != null) {
            System.out.println("result: " + resultStr);
        }
        ofdSemantemeJNI.destroy();
    }

    public static byte[] getFileByteArray(File file) {
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        byte[] buffer = null;
        try (FileInputStream fi = new FileInputStream(file)) {
            buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private static String byteToString(byte[] bytes) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }
        String strContent = "";
        try {
            strContent = new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strContent;
    }
}
