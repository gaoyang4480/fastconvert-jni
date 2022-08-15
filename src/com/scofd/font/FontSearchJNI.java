package com.scofd.font;

import java.awt.*;

public class FontSearchJNI {
    public native boolean initialize();

    public native void destroy();

    public native String findFontFilePath(String fontName);

    private static GraphicsEnvironment environment;

    public static void main(String[] args) {
//        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        Font[] fonts = environment.getAllFonts();
//        for (int i = 0; i < fonts.length; i++) {
//            System.out.println(fonts[i].toString());
//        }

        FontSearchJNI fontSearchJNI = new FontSearchJNI();
        System.loadLibrary("yh_fontsearch");
        if (!fontSearchJNI.initialize()) {
            return;
        }
        String fontFilePath = fontSearchJNI.findFontFilePath("Times New Roman");
        if (fontFilePath != null) {
            System.out.println("fontFilePath: " + fontFilePath);
        }
        fontSearchJNI.destroy();
    }

}
