package gui.view;

import gui.GuiCrawlingApi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utils.CommonsConstant.*;
import static utils.CommonsConstant.CSS_INFO;

public class CrawlingActionListener implements ActionListener {

    public JTextArea area;
    private Choice choiceYear;
    private Choice choiceMonth;
    public static final String HASHTAG_BIBLE_INFO_BOX = "#bibleinfo_box";
    public static final String HASHTAG_DAILY_BIBLE_INFO = "#dailybible_info";

    public CrawlingActionListener(JTextArea area, Choice choiceYear, Choice choiceMonth) {
        this.area = area;
        this.choiceYear = choiceYear;
        this.choiceMonth = choiceMonth;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        area.setText(BLANK);
        String year = choiceYear.getSelectedItem();
        String month = choiceMonth.getSelectedItem();
        JButton jButton = (JButton) event.getSource();
        String day = jButton.getText();
        System.out.println(year + HYPHEN + month + HYPHEN + day);
        String bible = year + HYPHEN + month + HYPHEN + day;
        StringBuilder url = new StringBuilder();
        url.append(JSOUP_CRAWLING_GUI_API)
                .append(bible)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE);

        try {
            Document document = Jsoup.connect(String.valueOf(url)).post();

            Element bible_text = GuiCrawlingApi.getHashtagBibleInfo(document, BIBLE_TEXT);

            Element bibleinfo_box = GuiCrawlingApi.getHashtagBibleInfo(document, HASHTAG_BIBLE_INFO_BOX);

            Element dailybible_info = GuiCrawlingApi.printHashtagDailyBilbeInfo(document, HASHTAG_DAILY_BIBLE_INFO);

            area.append(dailybible_info.text() + "\n");
            area.append(bible_text.text() + "\n");
            area.append(bibleinfo_box.text() + "\n");

            Elements liList = document.select(BODY_LIST_LI);
            for (Element li : liList) {
                String line = li.select(CSS_INFO).first().text();
                if (line.length() > 65) { // 너무 길면 줄바꿈 하기
                    line = line.substring(0, 36) + "\n" + line.substring(36, 66) + "\n" + line.substring(66) + "\n";
                    area.append(li.select(CSS_NUM).first().text() + COLON + line);
                } else if (line.length() > 35) {
                    line = line.substring(0, 36) + "\n" + line.substring(36) + "\n";
                    area.append(li.select(CSS_NUM).first().text() + COLON + line);
                } else {
                    area.append(li.select(CSS_NUM).first().text() + COLON + li.select(CSS_INFO).first().text() + "\n");
                }
                System.out.print(li.select(CSS_NUM).first().text() + COLON);
                System.out.println(li.select(CSS_INFO).first().text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
