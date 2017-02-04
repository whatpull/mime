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
     * 공통모듈을 이용하여 유틸을 작업하였습니다.
     * Parse Document Function
     * TODO [이연수]메타데이터 조회 필요
     *
     * @return title
     * @throws Exception
     */
    public static String parseDom() throws Exception {
        document = Jsoup.connect("http://google.com").get();
        String title = document.title();
        return title;
    }
}
