package console.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import static utils.CommonsConstant.JSOUP_CRAWLING_URL_BIBLE_TYPE;
import static utils.CommonsConstant.JSOUP_CRWALING_URL_BASE;

public class CrawlingApi {

    private static final String JSOUP_CRAWLING_URL = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";

    public static String initJsoupApiUrl(String urlDate) {
        return new StringBuilder().append(JSOUP_CRAWLING_URL)
                .append(JSOUP_CRWALING_URL_BASE)
                .append(urlDate)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE)
                .toString();
    }

    public static Document getBible(String url) throws IOException {
        return Jsoup.connect(url).post();
    }


}
