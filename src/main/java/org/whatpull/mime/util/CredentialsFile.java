package org.whatpull.mime.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * [유틸리티]인증처리
 * [Project] mime
 * [Package] org.whatpull.mime.util
 * Created by yeonsu on 2017-03-10
 */
public class CredentialsFile {

    public final static String PROFILE_FILE_NAME = "credentials";

    /**
     * 디렉토리 만들기
     * @param path 경로
     * @return 성공여부
     */
    private static boolean makeDirectory(String path) {
        if(StringUtils.isNoneBlank(path)) {
            File directory = new File(path);
            if(directory.exists()){
                File[] files = directory.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }else{
                directory.mkdirs();
            }
            return directory.exists();
        } else {
            return false;
        }
    }

    /**
     * 인증파일 생성
     * @param file 파일
     * @param path 경로
     * @return 성공여부
     */
    public static boolean createCredentials(File file, String path) {
        boolean result;
        if(makeDirectory(path)) {
            StringBuilder sb = new StringBuilder();
            sb.append(path);
            sb.append(PROFILE_FILE_NAME);
            if(file.renameTo(new File(sb.toString()))) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

}
