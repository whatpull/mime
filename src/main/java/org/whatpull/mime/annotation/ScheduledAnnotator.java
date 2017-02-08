package org.whatpull.mime.annotation;

import org.whatpull.mime.job.CrawlingLinkDataJob;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled Annotation Actor
 * 스케줄 어노테이션 생성자
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledAnnotator {

    private final static int INITIAL_DELAY = 0;                 // 초기지연시간
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method[] methods = clazz.getMethods();

        for(Method method : methods) {
            if(method.isAnnotationPresent(Scheduled.class)) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);
                TimeUnit unit = scheduled.unit();
                int period = scheduled.period();
                String seeds = scheduled.seeds();

                if(scheduledThreadPoolExecutor == null) {
                    scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
                }

                // Step1. 링크 크롤링
                CrawlingLinkDataJob linkDataJob = new CrawlingLinkDataJob(scheduledThreadPoolExecutor, seeds);
                scheduledThreadPoolExecutor.scheduleWithFixedDelay(linkDataJob, INITIAL_DELAY, period, unit);

                // Step2. 메타 크롤링



            }
        }
    }
}
