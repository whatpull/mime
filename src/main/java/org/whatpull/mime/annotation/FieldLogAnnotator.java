package org.whatpull.mime.annotation;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by yeonsu on 2017-01-30
 * since 2017-01-30
 */
public class FieldLogAnnotator {

    public static void setFieldLog(Class<?> clazz) throws NoSuchFieldException {
        Field name = clazz.getDeclaredField("name");

        if(ObjectUtils.allNotNull(name)) {
            Annotation annotation = name.getAnnotation(FieldLog.class);

            if(ObjectUtils.allNotNull(annotation)) {
                System.out.println("Time : " + String.valueOf(System.currentTimeMillis()));
            }
        }
    }
}
