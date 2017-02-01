package org.whatpull.mime.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Daemon Annotation
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Daemon {

}
