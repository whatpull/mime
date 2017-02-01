package org.whatpull.mime;

import org.whatpull.mime.annotation.Deamon;
import org.whatpull.mime.util.Scanner;

import java.util.Set;

/**
 * Created by user on 2017-02-01.
 */
public class MimeApplication {

    private final static String PACKAGE = "org.whatpull.mime";

    public static void main(String[] args) {

        try {
            Set<Class<?>> test = Scanner.getClasses(PACKAGE, Deamon.class);
            for(Class clazz : test) {
                System.out.print(clazz.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
