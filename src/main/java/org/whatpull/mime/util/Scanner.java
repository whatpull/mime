package org.whatpull.mime.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * [유틸리티]스캐너
 * [Project] mime
 * [Package] org.whatpull.mime.util
 * Created by yeonsu on 2017-03-10
 */
public class Scanner {

    /**
     *
     * 공통모듈을 이용하여 유틸을 작업하였습니다.
     * annotation class가 적용된 class 목록을 획득합니다.
     * URL : https://github.com/ronmamo/reflections 참고
     *
     * @param packageName pakage 이름
     * @param annotation 어노테이션 클래스
     * @return 적용클래스 목록
     * @throws Exception
     */
    public static Set<Class<?>> getClasses(String packageName, Class<?> annotation) throws Exception {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> singletons = reflections.getTypesAnnotatedWith((Class<? extends Annotation>) annotation);
        return singletons;
    }

}
