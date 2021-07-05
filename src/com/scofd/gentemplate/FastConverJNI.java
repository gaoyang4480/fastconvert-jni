package com.scofd.gentemplate;

import com.scofd.gentemplate.model.Attachments;
import com.scofd.gentemplate.model.MetaData;
import com.yh.scofd.agent.wrapper.model.TempInfo;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FastConverJNI {

    static final double convertCount = 1;

    public native int initConverter(String key, String configData);

    public native void finalizeConverter();

    public native byte[] convertImageToTemplateOFD(float docWidth, float docHeight, List<TempInfo> tempInfoList, byte[] formData);

    public native byte[] convert(String taskId, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, long sealId);

    public native String getErrMessage(int statusCode);

    public native String getLastError();

    public native String getVersion();

    public native String getMacAddr();

    public native String getCpuSerial();

    public native String getMainBoardSerial();

    public native String getMachineCode();

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: <模板基目录路径> <模板目录名>");
            return;
        }

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
        String templateBaseDir = args[0];
        byte[] configData = getFileByteArray(Paths.get(templateBaseDir, "config.xml").toFile());
        String configDataStr = byteToString(configData);
        int ret = convertJni.initConverter("", configDataStr);
        if (ret < 0) {
            String errMessage = convertJni.getErrMessage(ret);
            System.err.println("init converter error: " + errMessage + ret);
            return;
        }

        String templateDir = args[1];

        List<TempInfo> tempInfoList = new ArrayList<>();
        TempInfo tempInfo = new TempInfo();
        byte[] imageData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "Img_4.jpg").toFile());
        tempInfo.setFileData(imageData);
        tempInfo.setImageFormat("JPG");
        tempInfo.setDpi(96);
        tempInfo.setWidth(200);
        tempInfo.setHeight(200);
        tempInfo.setMmUnit(true);
        tempInfo.setPattern(true);
        tempInfo.setPatternWidth(50);
        tempInfo.setPatternHeight(50);
        tempInfoList.add(tempInfo);
        byte[] formData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "form.xml").toFile());
        //long startTime = System.currentTimeMillis();
        byte[] outputTemplateOFDData = convertJni.convertImageToTemplateOFD(0, 0, tempInfoList, formData);
        //System.out.println("转换耗时： " + (System.currentTimeMillis() - startTime) + "ms");
        if (outputTemplateOFDData != null) {
            OutputStream resultOutStream = new FileOutputStream(Paths.get(templateBaseDir, templateDir, "t.ofd").toString());
            resultOutStream.write(outputTemplateOFDData);
            resultOutStream.close();
        }



//        byte[] templateOFDData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "template.ofd").toFile());
//        byte[] inData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "data.xml").toFile());
//        // 构造元数据.
//        List<MetaData> metaDataList = new ArrayList<>();
//        MetaData metaData = new MetaData();
//        metaData.setName("test");
//        metaData.setValue("test value");
//        //metaDataList.add(metaData);
//        // 构造附件数据.
//        List<Attachments> attachmentDataList = new ArrayList<>();
//        Attachments attachments = new Attachments();
//        attachments.setName("original_invoice");
//        attachments.setFormat("xml");
//        attachments.setCreationDate("2020-07-28T12:38:11");
//        attachments.setModDate("2020-07-28T12:38:11");
//        attachments.setSize(2.415);
//        attachments.setVisible(true);
//        attachments.setFile("test".getBytes());
//        //attachmentDataList.add(attachments);
//
//        List<Callable<Integer>> list = null;
//        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(40);
//        long cc = System.currentTimeMillis();
//        try {
//            list = getCallableList(convertJni, templateOFDData, inData, metaDataList, attachmentDataList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Long beginTime = System.currentTimeMillis();
//            cachedThreadPool.invokeAll(list);
//            long endTime = System.currentTimeMillis();
//            long coustTime = endTime - beginTime;
//            System.out.println("**************************************************耗时:" + coustTime);
//            System.out.println("总耗时：" + (endTime - cc) + "毫秒");
//            System.out.println("平均每秒转换数：" + convertCount / ((endTime - cc) / 1000.0) + "");
//            System.out.println("平均：" + (endTime - cc) / convertCount);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 1; i++) {
//            byte[] resultData = convertJni.convert("001", templateOFDData, inData, metaDataList, attachmentDataList, 10);
//            if (resultData != null) {
//                OutputStream resultOutStream = new FileOutputStream(Paths.get(templateBaseDir, templateDir, "template_output.ofd").toString());
//                resultOutStream.write(resultData);
//                resultOutStream.close();
//                System.out.println("write result ofd successfully");
//            }
//        }
//        System.out.println("转换耗时： " + (System.currentTimeMillis() - startTime) + "ms");
//
//        convertJni.finalizeConverter();
//        System.out.println("convert end");
    }

    private static List<Callable<Integer>> getCallableList(FastConverJNI convertJni, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList) throws Exception {
        List<Callable<Integer>> resultList = new ArrayList<>();
        for (int j = 0; j < convertCount; j++) {
            final int i = j;
            Callable<Integer> callable = () ->
                    convert(convertJni, templateOFDData, inData, metaDataList, attachmentDataList, i);
            resultList.add(callable);
        }
        return resultList;
    }

    private static int convert(FastConverJNI convertJni, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, int i) throws IOException {
        byte[] resultData = convertJni.convert("001", templateOFDData, inData, metaDataList, attachmentDataList, 10);
        if (1 == 1 && resultData != null) {
            OutputStream resultOutStream = new FileOutputStream("D:\\gy\\c++\\projects\\template-ofd-converter\\data\\template-ofd-converter\\template4\\" + i + ".ofd");
            resultOutStream.write(resultData);
            resultOutStream.close();
            System.out.println("write result ofd successfully " + i + ".ofd");
        }
        return 0;
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
