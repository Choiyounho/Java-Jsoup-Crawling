package gui;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GuiCrawlingApi {

    public static Element printHashtagDailyBilbeInfo(Document document, String hashtagDailyBibleInfo) {
        Element dailybible_info = document.select(hashtagDailyBibleInfo).first();
        System.out.println(dailybible_info.text());
        return dailybible_info;
    }

    public static Element getHashtagBibleInfo(Document document, String hashtagBibleInfoBox) {
        return printHashtagDailyBilbeInfo(document, hashtagBibleInfoBox);
    }

}