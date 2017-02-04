package org.whatpull.mime.annotation;

import org.apache.commons.lang3.ObjectUtils;
import org.whatpull.mime.scheduled.ScheduledJob;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled Annotation Actor
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledAnnotator {

    private final static int DELAY = 1;                 // 지연시간(초)
    private final static int PERIOD = 3;                // 주기(초)

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method method = clazz.getMethod("mime", null);

        if(ObjectUtils.allNotNull(method)) {
            Annotation annotation = method.getAnnotation(Scheduled.class);

            if(ObjectUtils.allNotNull(annotation)) {
                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
                ScheduledJob job = new ScheduledJob(scheduledThreadPoolExecutor);
                scheduledThreadPoolExecutor.scheduleAtFixedRate(job, DELAY, PERIOD, TimeUnit.SECONDS);
            }
        }
    }

}
