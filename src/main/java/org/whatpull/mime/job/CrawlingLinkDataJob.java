package org.whatpull.mime.job;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;

import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Crawling Link Data Task Job
 * 크롤링 링크 데이터 처리 구현
 * Created by yeonsu on 2017-01-31.
 * since 2017-01-31
 */
public class CrawlingLinkDataJob implements Runnable {

    // THREAD CONTROL
    private static ScheduledThreadPoolExecutor executor;
    public static String seeds;

    // CONSTRUCTOR
    public CrawlingLinkDataJob(ScheduledThreadPoolExecutor executor, String seeds) {
        if(this.executor == null) {
            this.executor = executor;
        }
        if(StringUtils.isNoneBlank(seeds)) {
            this.seeds = seeds;
        }
    }

    public void run() {
        try {
            // TODO depth query need.
            Queue<String> dom = ParseDom.getLink(this.seeds);
            for(String link : dom) {
                System.out.println(link);
                AWS.insertLink(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdownNow();
        } finally {
            System.out.println("[STOP THREAD] ONE TIME CRAWLING LINK");
            ScheduledAnnotator.link.cancel(false);
        }
    }
}
