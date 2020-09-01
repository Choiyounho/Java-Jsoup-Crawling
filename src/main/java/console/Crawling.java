package console;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Crawling {

    private static final String JSOUP_CRAWLING_URL = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
    private static final String INPUT_DATE = "[입력->년(yyyy)-월(mm)-일(dd)]:";
    private static final String JSOUP_CRAWLING_URL_BIBLE_TYPE = "&bibleType=1";
    private static final String JSOUP_CRWALING_URL_BASE = "&Base_de=";
    private static final String LINE = "================================";
    private static final String COLON = ":";
    private static final String BODY_LIST_LI = ".body_list > li";
    private static final String CSS_NUM = ".num";
    private static final String CSS_INFO = ".info";

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String urlDate = inputDate(bufferedReader);

            String url = initJoupApiUrl(urlDate);

            System.out.println(LINE);

            Document document = getBibleText(url);

            printElements(document);

            System.out.println(LINE);
        } catch (IOException e) {
            System.out.println("IOException e" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }
    }

    private static String inputDate(BufferedReader inputStreamReader) throws IOException {
        System.out.print(INPUT_DATE);
        return inputStreamReader.readLine();
    }

    private static String initJoupApiUrl(String urlDate) {
        StringBuilder url = new StringBuilder();
        url.append(JSOUP_CRAWLING_URL)
                .append(JSOUP_CRWALING_URL_BASE)
                .append(urlDate)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE);
        return url.toString();
    }

    private static Document getBibleText(String url) throws IOException {
        Document document = Jsoup.connect(url).post();
        return document;
    }

    private static void printElements(Document document){
        Elements liList = document.select(BODY_LIST_LI);
        for (Element li : liList) {
            System.out.println(li.select(CSS_NUM).first().text() + COLON);
            System.out.println(li.select(CSS_INFO).first().text());
        }
    }

}
