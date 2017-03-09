package org.whatpull.mime.job;

import org.whatpull.mime.annotation.ScheduledAnnotator;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * [작업]모든 Job이 종료되면, 최종 스레드를 shutdown(종료) 합니다.
 * [Project] mime
 * [Package] org.whatpull.mime.job
 * Created by yeonsu on 2017-03-10
 */
public class ScheduledShutdownJob implements Runnable {

    // THREAD CONTROL
    private static ScheduledThreadPoolExecutor executor;

    // CONSTRUCTOR
    public ScheduledShutdownJob(ScheduledThreadPoolExecutor executor) {
        if(this.executor == null) {
            this.executor = executor;
        }
    }

    public void run() {
        boolean linkTaskIsStop = ScheduledAnnotator.link.isCancelled();
        boolean metaTaskIsStop = ScheduledAnnotator.meta.isCancelled();
        if(linkTaskIsStop && metaTaskIsStop) {
            executor.shutdownNow();
        } else {
            System.out.println("[RUNNING TASK] NOT STOP TASK");
        }
    }
}
