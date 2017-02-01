package org.whatpull.mime.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 스캐너 어노테이션 기반
 * Created by user on 2017-02-01.
 */
public class Scanner {

    public static Set<Class<?>> getClasses(String packageName, Class<?> annotation) throws Exception {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> singletons = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) annotation);
        return singletons;
    }

}
