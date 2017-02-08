package org.whatpull.mime.scheduled;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;

import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Schedule Task Job
 * 스케줄 처리 구현
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledJob implements Runnable {

    // 병렬처리 재사용(cache)
    private static ScheduledThreadPoolExecutor executor;
    public static String seeds = "http://www.google.com";

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
            // TODO [이연수]접근가능한 LINK 가 없는 경우 종료
            Queue<String> queue = AWS.selectLink();
            if(queue.size() > 0) {
                for (String seeds : queue) {
                    Queue<String> dom = ParseDom.parseDom(seeds);
                    for(String link : dom) {
                        System.out.println(link);
                        AWS.insertLink(link);
                    }
                }
            } else {
                Queue<String> dom = ParseDom.parseDom(this.seeds);
                for(String link : dom) {
                    System.out.println(link);
                    AWS.insertLink(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdown();
        } finally {
        }
    }
}
