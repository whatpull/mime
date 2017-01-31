package org.whatpull.mime.scheduled;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledJob extends TimerTask{

    public void run() {
        System.out.println(new Date());
    }
}
