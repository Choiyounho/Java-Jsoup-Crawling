package console.view;

import console.domain.CrawlingApi;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static console.domain.CrawlingApi.*;

public class Crawling {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String urlDate = CrawlingApi.inputDate(bufferedReader);

            String url = CrawlingApi.initJoupApiUrl(urlDate);

            System.out.println(LINE);

            Document document = CrawlingApi.getBibleText(url);

            printBibleText(document);

            printBibleInfo(document);

            document = getBibleText(url);

            printElements(document);

            System.out.println(LINE);
        } catch (IOException e) {
            System.out.println("IOException e" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }
    }

}
