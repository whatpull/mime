package org.whatpull.mime.util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Confirm Utils
 * 확인을 위한 유틸리티
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class Confirm {

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
}
