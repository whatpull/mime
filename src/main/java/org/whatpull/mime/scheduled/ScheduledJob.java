package org.whatpull.mime.scheduled;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.util.ParseDom;

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
    private String seeds = "http://www.instarwaz.com" ;

    // 생성자
    public ScheduledJob(ScheduledThreadPoolExecutor executor, String seeds) {
        if(executor == null) {
            this.executor = executor;
        }
        if(StringUtils.isNoneBlank(seeds)) {
            this.seeds = seeds;
        }
    }

    public void run() {
        try {
            ParseDom.parseDom(seeds);
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdown();
        }
    }
}
