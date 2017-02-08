package org.whatpull.mime.util;

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
     * Parse Document Function
     * TODO [이연수]메타데이터 조회 필요
     *
     * @param url Document Connection URL
     * @return 관련 Link URL 목록
     * @throws Exception
     */
    public static Queue<String> parseDom(String url) throws Exception {
        // TODO. URL 규칙찾기(NORMAL)
//        String regex = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
        String regex = "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$";

        document = Jsoup.connect(url).get();
        Elements links = document.select("a[href]");
        Queue<String> link = new LinkedList<String>();
        for(Element e : links) {
            if(e.attr("href").matches(regex)) {
                link.add(e.attr("href").toString());
            }
        }
        return link;
    }
}
