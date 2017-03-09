package org.whatpull.mime;

import org.whatpull.mime.util.AWS;
import org.whatpull.mime.gui.view.MainView;

/**
 * [실행]어플리케이션
 * [Project] mime
 * [Package] org.whatpull.mime
 * Created by yeonsu on 2017-03-10
 */
public class MimeApplication {

    private final static String PACKAGE = "org.whatpull.mime";

    /**
     * 실행 메인스레드
     * @param args 파라미터
     */
    public static void main(String[] args) {
        // MainView 초기화
        MainView.init();
        try {
//            Set<Class<?>> classes = Scanner.getClasses(PACKAGE, Daemon.class);
//            for(Class clazz : classes) {
//                // 스케줄 어노테이션 셋팅
//                ScheduledAnnotator.setScheduled(clazz);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

}
