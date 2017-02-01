package org.whatpull.mime;

import org.whatpull.mime.annotation.Deamon;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.Scanner;

import java.util.Set;

/**
 * Created by user on 2017-02-01.
 */
public class MimeApplication {

    private final static String PACKAGE = "org.whatpull.mime";

    /**
     * 실행 메인스레드
     * TODO. 지정 도메인 -> 서브도메인을 획득
     * @param args 파라미터
     */
    public static void main(String[] args) {
        try {
            Set<Class<?>> classes = Scanner.getClasses(PACKAGE, Deamon.class);
            for(Class clazz : classes) {
                // 스케줄 어노테이션 셋팅
                ScheduledAnnotator.setScheduled(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
