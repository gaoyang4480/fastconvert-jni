package com.scofd.gentemplate;

import com.scofd.gentemplate.model.Attachments;
import com.scofd.gentemplate.model.MetaData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FastConverJNI {

    public native int initConverter(String key, String configData);

    public native void finalizeConverter();

    public native byte[] convert(String taskId, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, long sealId);

    public native String getErrMessage(int statusCode);

    public native String getLastError();

    public native String getVersion();

    public native String getMacAddr();

    public native String getCpuSerial();

    public native String getMainBoardSerial();

    public native String getMachineCode();

    public static void main(String[] args) throws IOException {
        System.loadLibrary("converter");
        FastConverJNI convertJni = new FastConverJNI();

        String version = convertJni.getVersion();
        System.out.println("version: " + version);

        String macAddr = convertJni.getMacAddr();
        System.out.println("macAddr: " + macAddr);

        String cpuSerialNumber = convertJni.getCpuSerial();
        System.out.println("cpuSerialNumber: " + cpuSerialNumber);

        String mainBoardSerialNumber = convertJni.getMainBoardSerial();
        System.out.println("mainBoardSerialNumber: " + mainBoardSerialNumber);

        String machineCode = convertJni.getMachineCode();
        System.out.println("machineCode: " + machineCode);

        System.out.println("convert begin");
        byte[] configData = getFileByteArray(new File("C:\\Users\\gaoyang\\Downloads\\template-ofd-converter\\config.xml"));
        String configDataStr = byteToString(configData);
        int ret = convertJni.initConverter("", configDataStr);
        if (ret < 0) {
            String errMessage = convertJni.getErrMessage(ret);
            System.err.println("init converter error: " + errMessage + ret);
            return;
        }

        byte[] templateOFDData = getFileByteArray(new File("C:\\Users\\gaoyang\\Downloads\\template-ofd-converter\\template1\\template.ofd"));
        byte[] inData = getFileByteArray(new File("C:\\Users\\gaoyang\\Downloads\\template-ofd-converter\\template1\\data.xml"));
        // 构造元数据.
        List<MetaData> metaDataList = new ArrayList<>();
        MetaData metaData = new MetaData();
        metaData.setName("test");
        metaData.setValue("test value");
        metaDataList.add(metaData);
        // 构造附件数据.
        List<Attachments> attachmentDataList = new ArrayList<>();
        Attachments attachments = new Attachments();
        attachments.setName("original_invoice");
        attachments.setFormat("xml");
        attachments.setCreationDate("2020-07-28T12:38:11");
        attachments.setModDate("2020-07-28T12:38:11");
        attachments.setSize(2.415);
        attachments.setVisible(true);
        attachments.setFile("test".getBytes());
        attachmentDataList.add(attachments);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            byte[] resultData = convertJni.convert("001", templateOFDData, inData, metaDataList, attachmentDataList, 10);
            if (resultData != null) {
                OutputStream resultOutStream = new FileOutputStream("./template_output.ofd");
                resultOutStream.write(resultData);
                resultOutStream.close();
                System.out.println("write result ofd successfully");
            }
        }
        System.out.println("转换耗时： " + (System.currentTimeMillis() - startTime) + "ms");

        convertJni.finalizeConverter();
        System.out.println("convert end");
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

}
