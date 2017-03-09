package org.whatpull.mime.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * [어노테이션]스케줄
 * [Project] mime
 * [Package] org.whatpull.mime.annotation
 * Created by yeonsu on 2017-03-10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {

    TimeUnit unit() default TimeUnit.SECONDS;           // 단위
    int period() default 60;                            // 주기
    String seeds() default "http://www.google.com";   // 서치 URL
    int depth() default 0;                              // 반복

}
