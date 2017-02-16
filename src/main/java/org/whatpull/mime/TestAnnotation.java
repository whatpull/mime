package org.whatpull.mime;

import org.whatpull.mime.annotation.Daemon;
import org.whatpull.mime.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * Created by yeonsu on 2017-02-01.
 */
@Daemon
public class TestAnnotation {

    /**
     * 예제입니다.
     */
    @Scheduled(unit = TimeUnit.SECONDS, period = 5, seeds = "http://ohou.se", depth = 0)
    public void mime(String seed, int depth) {

    }

}
