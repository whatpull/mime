package org.whatpull.mime.job;

import org.whatpull.mime.annotation.ScheduledAnnotator;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by yeonsu on 2017-02-09.
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
