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
        try {
            String url = CrawlingApi.initJsoupApiUrl(CrawlingView.inputDate());

            CrawlingView.printLine();

            Document document = CrawlingApi.getBible(url);
            printBibleText(document);
            printBibleInfo(document);
            document = getBible(url);

            printElements(document);

            CrawlingView.printLine();
        } catch (IOException e) {
            System.out.println("IOException e" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception e" + e.getMessage());
        }
    }

}
