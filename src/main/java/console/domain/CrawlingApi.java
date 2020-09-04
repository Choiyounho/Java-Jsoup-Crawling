package console.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;

public class CrawlingApi {

    public CrawlingApi(){}

    public static final String LINE = "================================";

    private static final String INPUT_DATE = "[입력->년(yyyy)-월(mm)-일(dd)]:";

    private static final String JSOUP_CRAWLING_URL = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
    private static final String JSOUP_CRAWLING_URL_BIBLE_TYPE = "&bibleType=1";
    private static final String JSOUP_CRWALING_URL_BASE = "&Base_de=";
    private static final String COLON = ":";
    private static final String BODY_LIST_LI = ".body_list > li";
    private static final String CSS_NUM = ".num";
    private static final String CSS_INFO = ".info";
    private static final String BIBLE_TEXT = ".bible_text";
    private static final String BIBLE_INFO_BOX = ".bibleinfo_box";


    public static String inputDate(BufferedReader inputStreamReader) throws IOException {
        System.out.print(INPUT_DATE);
        return inputStreamReader.readLine();
    }

    public static String initJoupApiUrl(String urlDate) {
        StringBuilder url = new StringBuilder();
        url.append(JSOUP_CRAWLING_URL)
                .append(JSOUP_CRWALING_URL_BASE)
                .append(urlDate)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE);
        return url.toString();
    }

    public static Document getBibleText(String url) throws IOException {
        Document document = Jsoup.connect(url).post();
        return document;
    }

    public static void printBibleText(Document document) {
        Element bible_text = document.select(BIBLE_TEXT).first();
        System.out.println(bible_text.text());
    }

    public static void printBibleInfo(Document document) {
        Element bibleinfo_box = document.select(BIBLE_INFO_BOX).first();
        System.out.println(bibleinfo_box.text());
    }

    public static void printElements(Document document) {
        Elements liList = document.select(BODY_LIST_LI);
        for (Element li : liList) {
            System.out.println(li.select(CSS_NUM).first().text() + COLON);
            System.out.println(li.select(CSS_INFO).first().text());
        }
    }
}
