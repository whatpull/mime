package org.whatpull.mime.job;

import org.apache.commons.lang3.StringUtils;
import org.whatpull.mime.util.AWS;
import org.whatpull.mime.util.ParseDom;

import java.util.Queue;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Crawling Link Data Task Job
 * 스케줄 처리 구현
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class CrawlingLinkDataJob implements Runnable {

    // 병렬처리 재사용(cache)
    private static ScheduledThreadPoolExecutor executor;
    public static String seeds;

    // 생성자
    public CrawlingLinkDataJob(ScheduledThreadPoolExecutor executor, String seeds) {
        if(executor == null) {
            this.executor = executor;
        }
        if(StringUtils.isNoneBlank(seeds)) {
            this.seeds = seeds;
        }
    }

    public void run() {
        // 연결가능한 링크를 파악합니다.
        int isContinue = 0;
        try {
            // TODO [이연수]접근가능한 LINK 가 없는 경우 종료
            Queue<String> queue = AWS.selectLink();
            if(queue.size() > 0) {
                // 1회만 크롤링하기 위해 임시 주석
//                for (String seeds : queue) {
//                    Queue<String> dom = ParseDom.parseDom(seeds);
//                    isContinue += dom.size();
//                    for(String link : dom) {
//                        System.out.println(link);
//                        AWS.insertLink(link);
//                    }
//                }
            } else {
                Queue<String> dom = ParseDom.parseDom(this.seeds);
                isContinue += dom.size();
                for(String link : dom) {
                    System.out.println(link);
                    AWS.insertLink(link);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdown();
        } finally {
            System.out.println("isContinue : " + isContinue);
            if(isContinue == 0) {
                // 현재 태스크를 제거합니다.
                executor.remove(this);
            }
        }
    }
}
