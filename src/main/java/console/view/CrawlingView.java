package console.view;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static utils.CommonsConstant.*;

public class CrawlingView {

    private static final String INPUT_DATE = "[입력->년(yyyy)-월(mm)-일(dd)]:";
    private static final String LINE = "================================";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static String inputDate() throws IOException {
        System.out.print(INPUT_DATE);
        return bufferedReader.readLine();
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
        liList.stream()
                .forEach(li -> {
                    System.out.print(li.select(CSS_NUM).first().text() + COLON);
                    System.out.println(li.select(CSS_INFO).first().text());
                });
    }

    public static void printLine() {
        System.out.println(LINE);
    }

}
