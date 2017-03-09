package org.whatpull.mime.job;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.annotation.ScheduledAnnotator;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;
import org.whatpull.mime.util.Split;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * [작업]크롤링 메타 데이터 처리 구현
 * [Project] mime
 * [Package] org.whatpull.mime.job
 * Created by yeonsu on 2017-03-10
 */
public class CrawlingMetaDataJob implements Runnable {

    // THREAD CONTROL
    private static ScheduledThreadPoolExecutor executor;
    public static String seeds;


    // CONSTRUCTOR
    public CrawlingMetaDataJob(ScheduledThreadPoolExecutor executor, String seeds) {
        if(this.executor == null) {
            this.executor = executor;
        }
        if(StringUtils.isNoneBlank(seeds)) {
            this.seeds = seeds;
        }
    }

    public void run() {
        try {
            Queue<String> queue = AWS.selectLink();
            if(queue.size() > 0) {
                for (String seeds : queue) {
                    Queue<String> dom = new LinkedList<String>();
                    try {
                        dom.addAll(ParseDom.getMeta(seeds));
                    } catch (Exception e) {
                        System.out.println("[EXCEPTION] MESSAGE : " + e.getMessage());
                    } finally { // 무조건 실행합니다.
                        if(dom.size() > 0) {
                            for (String meta : dom) {
                                AWS.insertMeta(seeds, meta);
                                AWS.deleteLink(seeds);
                                Queue<String> words = Split.splitString(meta);
                                for(String word : words) {
                                    AWS.insertIndex(word, seeds);
                                }
                            }
                        } else {
                            System.out.println("[DELETE LINK] NOT FOUND METADATA");
                            AWS.deleteLink(seeds);
                        }
                    }
                }
            } else {
                System.out.println("[STOP THREAD] NOT FOUND CRAWLING LINK");
                ScheduledAnnotator.meta.cancel(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
    }

}
