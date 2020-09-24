package gui.domain;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GuiCrawlingApi {

    public static Element printHashtagDailyBilbeInfo(Document document, String hashtagDailyBibleInfo) {
        return document.select(hashtagDailyBibleInfo).first();
    }

    public static Element getHashtagBibleInfo(Document document, String hashtagBibleInfoBox) {
        return printHashtagDailyBilbeInfo(document, hashtagBibleInfoBox);
    }

}