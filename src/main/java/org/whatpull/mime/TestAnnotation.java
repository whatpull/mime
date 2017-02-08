package org.whatpull.mime;

import org.whatpull.mime.annotation.Daemon;
import org.whatpull.mime.annotation.Scheduled;

/**
 * Created by user on 2017-02-01.
 */
@Daemon
public class TestAnnotation {

    @Scheduled(period = 10)
    public void mime() {

    }

}
