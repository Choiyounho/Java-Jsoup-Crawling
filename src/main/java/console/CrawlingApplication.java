package console;

import console.domain.CrawlingApi;
import console.view.CrawlingView;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static console.domain.CrawlingApi.*;
import static console.view.CrawlingView.*;


public class CrawlingApplication {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String urlDate = CrawlingView.inputDate(bufferedReader);
            String url = CrawlingApi.initJoupApiUrl(urlDate);

            System.out.println(LINE);

            Document document = CrawlingApi.getBible(url);
            printBibleText(document);
            printBibleInfo(document);
            document = getBible(url);

            printElements(document);

            System.out.println(LINE);
        } catch (IOException e) {
            System.out.println("IOException e" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }
    }

}
