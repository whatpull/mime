package org.whatpull.mime.job;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;

import java.util.Iterator;
import java.util.LinkedList;
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
    private static int depth;
    private static Queue<String> seeds = new LinkedList<String>();

    // CONSTRUCTOR(생성자는 1회)
    public CrawlingLinkDataJob(ScheduledThreadPoolExecutor executor, Queue<String> seeds, int depth) {
        if(this.executor == null) {
            this.executor = executor;
        }
        if(seeds.size() > 0) {
            this.seeds.addAll(seeds);
        }
        if(depth > 0) {
            this.depth = depth;
        }
    }

    public void run() {
        try {
            String seed;
            while (StringUtils.isNoneBlank((seed = seeds.poll()))) {
                Queue<String> dom = ParseDom.getLink(seed);
                for(String link : dom) {
                    System.out.println(link);
                    AWS.insertLink(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdownNow();
        } finally {
            if(this.depth == 0) {
                System.out.println("[STOP THREAD] ONE TIME CRAWLING LINK");
                ScheduledAnnotator.link.cancel(false);
            } else {
                this.depth--;
                Queue<String> link = AWS.selectLink();
                seeds.addAll(link);
            }
        }
    }
}
