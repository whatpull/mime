package org.whatpull.mime.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Confirm Utils
 * 확인을 위한 유틸리티
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class Confirm {

    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    protected static OSType detectedOS;

    /**
     * URL 접속가능 여부 확인
     * @param URL 접속 URL
     * @return 존재유무
     */
    public static boolean exists(String URL) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) new URL(URL).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 현재 OS 타입 확인
     * @return
     */
    public static OSType getOperatingSystemType() {
        if(detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if((OS.indexOf("mac") > 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if(OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if(OS.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }


}
