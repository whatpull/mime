package org.whatpull.mime.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [어노테이션]데몬
 * [Project] mime
 * [Package] org.whatpull.mime.annotation
 * Created by yeonsu on 2017-03-10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Daemon {

}
