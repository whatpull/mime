package org.whatpull.mime.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * [유틸리티]표준 정책
 * [Project] mime
 * [Package] org.whatpull.mime.util
 * Created by yeonsu on 2017-03-10
 */
public class Policy {

    private final static String BOT_NAME = "mime";
    private static Queue<String> queue;

    /**
     * robots.txt파일 로봇 배제 표준
     * @return Queue<String> 접근 제외 URL
     */
    public static Map<String, Object> getRobotsPolicy(String seeds) {
        Map<String, Object> result = new HashMap<String, Object>();
        queue = new LinkedList<String>();
        boolean isWorking = false;

        StringBuilder sb = new StringBuilder(seeds);
        sb.append("/robots.txt");

        try {
            URL url = new URL(sb.toString());
            Scanner scanner = new Scanner(url.openStream());

            while(scanner.hasNext()) {
                String robots = scanner.next();
                boolean binding = false;
                if(robots.contains("User-agent")) {
                    String bot = robots.substring(robots.indexOf(":")+1).trim();
                    if(BOT_NAME.equalsIgnoreCase(bot) || "*".equals(bot)) { // mime, * 일경우 정책확인
                        isWorking = true;
                        binding = true;
                    } else {
                        isWorking = false;
                        binding = false;
                    }
                } else if(robots.contains("Disallow")) {
                    if(binding) {
                        String disallow = robots.substring(robots.indexOf(":")+1).trim();
                        if(disallow.equals("/")) {
                            isWorking = false;
                            break;
                        } else {
                            queue.add(disallow);
                        }
                    } else {
                        isWorking = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            result.put("isWorking", isWorking);
            result.put("disallow", queue);
        }

        return result;
    }

    // TODO 메타태그 정책 추가

}
