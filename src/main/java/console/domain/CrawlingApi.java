package console.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlingApi {


    private static final String JSOUP_CRAWLING_URL = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
    private static final String JSOUP_CRAWLING_URL_BIBLE_TYPE = "&bibleType=1";
    private static final String JSOUP_CRWALING_URL_BASE = "&Base_de=";

    public static String initJoupApiUrl(String urlDate) {
        StringBuilder url = new StringBuilder();
        url.append(JSOUP_CRAWLING_URL)
                .append(JSOUP_CRWALING_URL_BASE)
                .append(urlDate)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE);
        return url.toString();
    }

    public static Document getBible(String url) throws IOException {
        Document document = Jsoup.connect(url).post();
        return document;
    }


}
