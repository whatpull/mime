package org.whatpull.mime.annotation;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by user on 2017-01-31.
 */
public class ScheduledAnnotator {

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method method = clazz.getMethod("scheduled", null);

        if(ObjectUtils.allNotNull(method)) {
            Annotation annotation = method.getAnnotation(Scheduled.class);

            if(ObjectUtils.allNotNull(annotation)) {
                System.out.println("Time : " + String.valueOf(System.currentTimeMillis()));
            }
        }
    }

}
