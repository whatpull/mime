package org.whatpull.mime.annotation;

import org.whatpull.mime.scheduled.ScheduledJob;
import org.whatpull.mime.util.AWS;

import java.lang.reflect.Method;
import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled Annotation Actor
 * 스케줄 어노테이션 생성자
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledAnnotator {

    private final static int INITIAL_DELAY = 10;                 // 지연시간(분)
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method[] methods = clazz.getMethods();

        for(Method method : methods) {
            if(method.isAnnotationPresent(Scheduled.class)) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);
                int period = scheduled.period();
                if(scheduledThreadPoolExecutor == null) {
                    scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
                }
                ScheduledJob job = new ScheduledJob(scheduledThreadPoolExecutor, null);
                scheduledThreadPoolExecutor.scheduleWithFixedDelay(job, INITIAL_DELAY, period, TimeUnit.SECONDS);
            }
        }
    }
}
