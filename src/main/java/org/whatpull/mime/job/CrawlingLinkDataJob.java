package org.whatpull.mime.job;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;
import org.whatpull.mime.util.Policy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * [작업]크롤링 링크 데이터 처리 구현
 * [Project] mime
 * [Package] org.whatpull.mime.job
 * Created by yeonsu on 2017-03-10
 */
public class CrawlingLinkDataJob implements Runnable {

    // THREAD CONTROL
    private static ScheduledThreadPoolExecutor executor;
    private static int depth;
    private static Queue<String> seeds = new LinkedList<String>();
    private static Map<String, Object> map;

    // CONSTRUCTOR(생성자는 1회)
    public CrawlingLinkDataJob(ScheduledThreadPoolExecutor executor, Queue<String> seeds, int depth, Map<String, Object> map) {
        if(this.executor == null) {
            this.executor = executor;
        }
        if(seeds.size() > 0) {
            this.seeds.addAll(seeds);
        }
        if(depth > 0) {
            this.depth = depth;
        }
        // 맵 초기화
        this.map = map;
    }

    public void run() {
        try {
            String seed;
            while (StringUtils.isNoneBlank((seed = seeds.poll()))) {
                Queue<String> policy = (Queue<String>) map.get("policy");
                if(policy.contains(seed)) {
                    // TODO 로그추가 정책에서 제외된 URL
                } else {
                    Queue<String> dom = ParseDom.getLink(seed);
                    for(String link : dom) {
                        System.out.println(link);
                        AWS.insertLink(link);
                    }
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
