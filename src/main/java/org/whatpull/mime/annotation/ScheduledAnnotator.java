package org.whatpull.mime.annotation;

import org.apache.commons.lang3.ObjectUtils;
import org.whatpull.mime.scheduled.ScheduledJob;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Timer;

/**
 * Created by user on 2017-01-31.
 */
public class ScheduledAnnotator {

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method method = clazz.getMethod("mime", null);

        if(ObjectUtils.allNotNull(method)) {
            Annotation annotation = method.getAnnotation(Scheduled.class);

            if(ObjectUtils.allNotNull(annotation)) {
                ScheduledJob job = new ScheduledJob();
                Timer jobScheduler = new Timer(true);
                jobScheduler.scheduleAtFixedRate(job, 1000, 3000);

                try { // 20초간 실행
                    Thread.sleep(20000);
                } catch(InterruptedException ex) {
                    //
                }
            }
        }
    }

}
