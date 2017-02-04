package org.whatpull.mime.scheduled;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Schedule Task Job
 * TODO. 시드도메인 -> 하위 URL 획득
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledJob implements Runnable {

    // 병렬처리 재사용(cache)
    private static ScheduledThreadPoolExecutor executor;

    // 생성자
    public ScheduledJob(ScheduledThreadPoolExecutor executor) {
        if(executor == null) {
            this.executor = executor;
        }
    }

    public void run() {
        try {
            System.out.println(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdown();
        }
    }
}
