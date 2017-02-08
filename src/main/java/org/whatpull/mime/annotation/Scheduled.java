package org.whatpull.mime.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled Annotation
 * [Method]스케줄 어노테이션
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

    TimeUnit unit() default TimeUnit.SECONDS;           // 단위
    int period() default 60;                            // 주기
    String seeds() default "http://www.google.com";     // 서치 URL

}
