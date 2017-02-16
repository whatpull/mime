package org.whatpull.mime.util;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yeonsu on 2017-02-10.
 */
public class Split {

    /**
     * TODO 조사제거 및 서술어제거(단어기반)
     * @param meta
     * @return
     */
    public static Queue<String> splitString(String meta) {
        Queue<String> queue = new LinkedList<String>();
//        Pattern pattern = Pattern.compile("\\w+");
        Pattern pattern = Pattern.compile("\\b*[(가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9)]*\\b");
        Matcher matcher = pattern.matcher(meta);
        while (matcher.find()) {
            if(StringUtils.isNoneBlank(matcher.group())) {
                System.out.println(matcher.group());
                queue.add(matcher.group());
            }
        }
        return queue;
    }

}