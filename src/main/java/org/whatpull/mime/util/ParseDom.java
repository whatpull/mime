package org.whatpull.mime.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
     * @param url
     * @return title
     * @throws Exception
     */
    public static String parseDom(String url) throws Exception {
        String regex = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";

        document = Jsoup.connect(url).get();
        String title = document.title();
        Elements links = document.select("a[href]");
        for(Element e : links) {
            if(e.attr("href").matches(regex)) {
                System.out.println("link : " + e.attr("href").toString());
            }
        }
        return title;
    }
}
