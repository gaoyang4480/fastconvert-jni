package com.scofd.gentemplate;

import com.scofd.gentemplate.model.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import cn.hutool.core.codec.Base64;

public class FastConverJNI {

    static final double convertCount = 1;

    public native int initConverter(String key, String configData);

    public native void finalizeConverter();

    public native byte[] convertImageToTemplateOFD(float docWidth, float docHeight, List<TempInfo> tempInfoList, byte[] formData);

    public native byte[] convert(String taskId, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, long sealId);

    public native byte[] convertWithClipFont(String taskId, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, long sealId,
                                             List<String> clipFontNames, boolean isClipFont);

    public native byte[] streamConvertWithClipFont(String taskId, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, long sealId,
                                                   List<String> clipFontNames, boolean isClipFont, boolean isStreamConvert);

    public native String getErrMessage(int statusCode);

    public native String getLastError();

    public native String getVersion();

    public native String getMacAddr();

    public native String getCpuSerial();

    public native String getMainBoardSerial();

    public native String getHDID();

    public native String getMachineCode();

    public native String[] addAudio(String taskId, byte[] templateOFDData, List<MultiMediaData> audioDataList);

    public native String[] addVideo(String taskId, byte[] templateOFDData, List<MultiMediaData> videoDataList);

    public native byte[] replaceAttachment(byte[] templateOFDData, List<Attachments> attachmentDataList);

    public native byte[] opgPackage(OPGPackageArgs opgPackageArgs);

    /**
     * 将file文件转换成Byte数组
     *
     * @param file 转换文件
     * @return Byte数组
     */
    public static byte[] getBytesByFile(File file) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        try {
            fis = new FileInputStream(file);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            //log.error("将文件转换成Byte数组失败", e);
        } finally {
            if (fis != null) {
                fis.close();
            }
            bos.close();
        }
        return null;
    }

    /**
     * @param bytes     byte数组
     * @param fileRoute 文件路径
     * @param fileName  文件名
     */
    public static void upload(byte[] bytes, String fileRoute, String fileName) {
        try {
            File directory = new File(fileRoute);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, fileName);
            BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
            bos.write(bytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Usage: <模板基目录路径> <模板目录名>");
            return;
        }

        String path = "C:\\Users\\gaoyang\\Downloads\\boos_cz_p7.txt";
        File file = new File(path);
        byte[] bytes = getBytesByFile(file);
        byte[] d = Base64.decode(new String(bytes));
        String dStr = bytesToHexString(d);
        upload(dStr.getBytes(StandardCharsets.UTF_8), "C:\\Users\\gaoyang\\Downloads\\", "boos_cz_p7.dat");

        System.loadLibrary("zlib");
        System.loadLibrary("icuuc");
        System.loadLibrary("icui18n");
        System.loadLibrary("v8_libbase");
        System.loadLibrary("v8_libplatform");
        System.loadLibrary("v8");
        System.loadLibrary("libopg");

        System.loadLibrary("converter");
        FastConverJNI convertJni = new FastConverJNI();

        String version = convertJni.getVersion();
        System.out.println("version: " + version);

//        String macAddr = convertJni.getMacAddr();
//        System.out.println("macAddr: " + macAddr);
//
//        String cpuSerialNumber = convertJni.getCpuSerial();
//        System.out.println("cpuSerialNumber: " + cpuSerialNumber);
//
//        String mainBoardSerialNumber = convertJni.getMainBoardSerial();
//        System.out.println("mainBoardSerialNumber: " + mainBoardSerialNumber);

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

//        OPGPackageArgs opgPackageArgs = new OPGPackageArgs();
//        List<OPGComponentData> componentDataList = new ArrayList<>();
//        OPGComponentData opgComponentData = new OPGComponentData();
//        opgComponentData.setTitle("hello");
//        opgComponentData.setComponentFilePath("D:\\gy\\ofd\\files\\hello.ofd");
//        List<Attachments> attachs = new ArrayList<>();
//        Attachments attachments1 = new Attachments();
//        attachments1.setName("test");
//        attachments1.setFilePath("C:\\Users\\gaoyang\\Pictures\\1.jpg");
//        attachs.add(attachments1);
//        opgComponentData.setAttachs(attachs);
//        componentDataList.add(opgComponentData);
//        opgPackageArgs.setComponentDataList(componentDataList);
//        byte[] opgPackagedData = convertJni.opgPackage(opgPackageArgs);
//        if (opgPackagedData != null) {
//            OutputStream resultOutStream = new FileOutputStream("test.opg");
//            resultOutStream.write(opgPackagedData);
//            resultOutStream.close();
//        }

        String templateDir = args[1];
        /*
        List<TempInfo> tempInfoList = new ArrayList<>();
        TempInfo tempInfo = new TempInfo();
        byte[] imageData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "Img_4.jpg").toFile());
        tempInfo.setFileData(imageData);
        tempInfo.setImageFormat("JPG");
        tempInfo.setDpi(96);
        tempInfo.setWidth(200);
        tempInfo.setHeight(200);
        tempInfo.setMmUnit(true);
//        tempInfo.setPattern(true);
//        tempInfo.setPatternWidth(50);
//        tempInfo.setPatternHeight(50);
        tempInfoList.add(tempInfo);
        tempInfo = new TempInfo();
        tempInfo.setColorVal("255 0 0");
        tempInfo.setDpi(96);
        tempInfo.setWidth(200);
        tempInfo.setHeight(200);
        tempInfo.setMmUnit(true);
        tempInfoList.add(tempInfo);
        byte[] formData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "form.xml").toFile());
        long startTime = System.currentTimeMillis();
        byte[] outputTemplateOFDData = convertJni.convertImageToTemplateOFD(0, 0, tempInfoList, formData);
        System.out.println("转换耗时： " + (System.currentTimeMillis() - startTime) + "ms");
        if (outputTemplateOFDData != null) {
            OutputStream resultOutStream = new FileOutputStream(Paths.get(templateBaseDir, templateDir, "t.ofd").toString());
            resultOutStream.write(outputTemplateOFDData);
            resultOutStream.close();
        }
        */

        ///*
        byte[] templateOFDData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "template.ofd").toFile());
        byte[] inData = getFileByteArray(Paths.get(templateBaseDir, templateDir, "data.xml").toFile());
        // 构造元数据.
        List<MetaData> metaDataList = new ArrayList<>();
        MetaData metaData = new MetaData();
        metaData.setName("certificateName");
        //metaData.setValue("刘𤧟刘");
        //metaData.setValue("兰考测试证照目录");
        //metaData.setValueData("刘𤧟刘".getBytes(StandardCharsets.UTF_8));
        metaData.setValueData("兰考测试证照目录".getBytes(StandardCharsets.UTF_8));
        metaDataList.add(metaData);
        // 构造附件数据.
        List<Attachments> attachmentDataList = new ArrayList<>();
        Attachments attachments = new Attachments();
        attachments.setName("fujian/1/经营许可证");
        attachments.setFormat("svg");
        attachments.setCreationDate("2020-07-28T12:38:11");
        attachments.setModDate("2020-07-28T12:38:11");
        attachments.setSize(2.415);
        attachments.setVisible(true);
        //attachments.setFile("test".getBytes());
        attachments.setFilePath("D:\\math.svg");
        //attachments.setAttachRelativePath("test/test1");
//        attachmentDataList.add(attachments);

//        byte[] srcOFDData = getFileByteArray(Paths.get("D:\\gd.ofd").toFile());
//        byte[] replaceOFDData = convertJni.replaceAttachment(srcOFDData, attachmentDataList);
//        OutputStream resultOutStream = new FileOutputStream("D:\\0.ofd");
//        resultOutStream.write(replaceOFDData);
//        resultOutStream.close();

//        String[] attachIdList = convertJni.setAttachment("001", templateOFDData, attachmentDataList);
//        if (attachIdList != null) {
//            for (int i = 0; i < attachIdList.length; ++i) {
//                System.out.println("attachId: " + attachIdList[i]);
//            }
//        }

        List<Callable<Integer>> list = null;
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(8);
        long cc = System.currentTimeMillis();
        try {
            list = getCallableList(convertJni, templateOFDData, inData, metaDataList, attachmentDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Long beginTime = System.currentTimeMillis();
            cachedThreadPool.invokeAll(list);
            long endTime = System.currentTimeMillis();
            long coustTime = endTime - beginTime;
            System.out.println("**************************************************耗时:" + coustTime);
            System.out.println("总耗时：" + (endTime - cc) + "毫秒");
            System.out.println("平均每秒转换数：" + convertCount / ((endTime - cc) / 1000.0) + "");
            System.out.println("平均：" + (endTime - cc) / convertCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //*/


//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 1; i++) {
//            byte[] resultData = convertJni.convert("001", templateOFDData, inData, metaDataList, attachmentDataList, 10);
////            if (resultData != null) {
////                OutputStream resultOutStream = new FileOutputStream(Paths.get(templateBaseDir, templateDir, "template_output.ofd").toString());
////                resultOutStream.write(resultData);
////                resultOutStream.close();
////                System.out.println("write result ofd successfully");
////            }
//        }
//        System.out.println("转换耗时： " + (System.currentTimeMillis() - startTime) + "ms");

        //convertJni.finalizeConverter();
        System.out.println("convert end");
    }

    private static List<Callable<Integer>> getCallableList(FastConverJNI convertJni, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList) throws Exception {
        List<Callable<Integer>> resultList = new ArrayList<>();
        for (int j = 0; j < 1; j++) {
            final int i = j;
            Callable<Integer> callable = () ->
                    convert(convertJni, templateOFDData, inData, metaDataList, attachmentDataList, i);
            resultList.add(callable);
        }
        return resultList;
    }

    private static int convert(FastConverJNI convertJni, byte[] templateOFDData, byte[] inData, List<MetaData> metaDataList, List<Attachments> attachmentDataList, int i) throws IOException {
        //byte[] copyTemplateOFDData = Arrays.copyOf(templateOFDData, templateOFDData.length);
        List<String> clipFontNames = new ArrayList<>();
        //clipFontNames.add("宋体");
        //byte[] resultData = convertJni.convertWithClipFont("001", templateOFDData, inData, metaDataList, attachmentDataList, 10, clipFontNames, true);

//        List<MultiMediaData> videoDataList = new ArrayList<>();
//        MultiMediaData multiMediaData = new MultiMediaData("C:\\Users\\gaoyang\\Videos\\2.mp4", "mp4");
//        videoDataList.add(multiMediaData);
//        String[] videoResIdList = convertJni.addVideo("001", templateOFDData, videoDataList);

        byte[] resultData = convertJni.convert("" + i, templateOFDData, inData, metaDataList, attachmentDataList, 10);
        if (1 == 1 && resultData != null) {
            //OutputStream resultOutStream = new FileOutputStream("D:\\gy\\c++\\projects\\template-ofd-converter\\data\\template-ofd-converter\\template_25\\" + i + ".ofd");
//            OutputStream resultOutStream = new FileOutputStream("./" + i + ".ofd");
//            resultOutStream.write(resultData);
//            resultOutStream.close();
            System.out.println("write result ofd successfully " + i + ".ofd");
        }
        //System.out.println("write result ofd successfully " + i + ".ofd");
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
