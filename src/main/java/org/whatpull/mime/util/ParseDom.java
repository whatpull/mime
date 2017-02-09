package org.whatpull.mime.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Parse Document Web Page
 * 웹 HTML 문서 파싱 유틸 클래스
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ParseDom {

    private static Document document;

    /**
     * 공통모듈을 이용하여 유틸을 작업하였습니다.
     * Parse Document Get Link Data Function
     *
     * @param url Document Connection URL
     * @return 관련 Link URL 목록
     * @throws Exception
     */
    public static Queue<String> getLink(String url) throws Exception {
        // TODO. URL 규칙찾기(NORMAL)
//        String regex = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
        String regex = "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$";

        document = Jsoup.connect(url).get();
        Elements elements = document.select("a[href]");
        Queue<String> link = new LinkedList<String>();
        for(Element element : elements) {
            if(element.attr("href").matches(regex)) {
                link.add(element.attr("href").toString());
            }
        }
        return link;
    }

    /**
     * 공통모듈을 이용하여 유틸을 작업하였습니다.
     * Parse Document Get Meta Data Function
     *
     * @param url Document Connection URL
     * @return 관련 Meta Content 목록
     * @throws Exception
     */
    public static Queue<String> getMeta(String url) throws Exception {
        if(StringUtils.isNoneBlank(url)) {
            document = Jsoup.connect(url).ignoreContentType(true).get();
            Elements elements = document.select("meta[name=description]");
            Queue<String> meta = new LinkedList<String>();
            for(Element element : elements) {
                meta.add(element.attr("content").toString());
            }
            return meta;
        } else {
            return new LinkedList<String>();
        }
    }
}