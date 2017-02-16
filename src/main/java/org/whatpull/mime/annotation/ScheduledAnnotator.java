package org.whatpull.mime.annotation;

import org.whatpull.mime.job.CrawlingLinkDataJob;
import org.whatpull.mime.job.CrawlingMetaDataJob;
import org.whatpull.mime.job.ScheduledShutdownJob;
import org.whatpull.mime.util.Policy;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled Annotation Actor
 * 스케줄 어노테이션 생성자
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ScheduledAnnotator {

    private final static int INITIAL_DELAY = 0;                 // 초기지연시간
    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    public static ScheduledFuture<?> link;
    public static ScheduledFuture<?> meta;
    public static String seeds;

    public static void setScheduled(Class<?> clazz) throws NoSuchMethodException {
        Method[] methods = clazz.getMethods();

        for(Method method : methods) {
            if(method.isAnnotationPresent(Scheduled.class)) {
                Scheduled scheduled = method.getAnnotation(Scheduled.class);

                /*Type[] types = method.getGenericParameterTypes();
                for(int i = 0; i < types.length; i++) {
                    if(types[i] instanceof ParameterizedType) {
                        Type[] parameters = ((ParameterizedType)types[i]).getActualTypeArguments();
                        System.out.print(parameters[0]);
                    }
                }*/

                TimeUnit unit = scheduled.unit();
                int period = scheduled.period();
                int depth = scheduled.depth();
                seeds = scheduled.seeds();

                // 정책
                Map<String, Object> map = Policy.getRobotsPolicy(seeds);
                Boolean isWorking = (Boolean) map.get("isWorking");
                if(isWorking) {
                    if(scheduledThreadPoolExecutor == null) {
                        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new ThreadPoolExecutor.DiscardOldestPolicy());
                    }

                    // 큐를 생성합니다.
                    Queue<String> queue = new LinkedList<String>();
                    queue.add(seeds);

                    // Step1. 링크 크롤링
                    CrawlingLinkDataJob linkDataJob = new CrawlingLinkDataJob(scheduledThreadPoolExecutor, queue, depth, map);
                    link = scheduledThreadPoolExecutor.scheduleWithFixedDelay(linkDataJob, INITIAL_DELAY, period, unit);

                    // Step2. 메타 크롤링
                    CrawlingMetaDataJob metaDataJob = new CrawlingMetaDataJob(scheduledThreadPoolExecutor, seeds);
                    meta = scheduledThreadPoolExecutor.scheduleWithFixedDelay(metaDataJob, INITIAL_DELAY, period, unit);

                    // step3. 종료 스케줄
                    ScheduledShutdownJob shutdownJob = new ScheduledShutdownJob(scheduledThreadPoolExecutor);
                    scheduledThreadPoolExecutor.scheduleWithFixedDelay(shutdownJob, INITIAL_DELAY, period, unit);
                } else {
                    // TODO 로그생성(크롤링 robots.txt 정책상 크롤링 금지)
                }
            }
        }
    }
}
