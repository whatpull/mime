package org.whatpull.mime.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Parse Document Web Page
 * 웹 HTML 문서 파싱 유틸 클래스
 * Created by yeonsu on 2017-01-31
 * since 2017-01-31
 */
public class ParseDom {

    private static Document document;

    /**
     * TODO 크롤링 테스트
     * Parse Document Function
     * @return title
     * @throws Exception
     */
    public static String parseDom() throws Exception {
        document = Jsoup.connect("http://google.com").get();
        String title = document.title();
        return title;
    }
}
