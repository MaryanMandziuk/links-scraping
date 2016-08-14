/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package links.scraping;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author maryan
 */
public class LinksScraping {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://leo-tvorit.livejournal.com/").timeout(5000).get();
        List<String> blackList = Arrays.asList("Карандаши", "Обзор");

//        StringBuilder links = new StringBuilder();
        List<String> links = new ArrayList<>();
        while (true) {
            Elements subjLink = doc.getElementsByClass("subj-link");

            Elements ljtags = doc.getElementsByClass("ljtags");

            for (int i = 0; i < ljtags.size(); i++) {
                Elements tegs = ljtags.get(i).getElementsByTag("a");
                boolean check = false;

                for (Element e : tegs) {
                    if (blackList.contains(e.text())) {
                        check = true;
                    } else {
                        check = false;
                        break;
                    }
                }

                if (!check) {
                    links.add(subjLink.get(i).attr("href"));
                }

            }

            Element nextPage = doc.getElementsByClass("prev").select("a").first();
            try {
                doc = Jsoup.connect(nextPage.attr("href")).timeout(5000).get();
            } catch (Exception e) {
                System.out.println("Finish!");
                break; 
            }
        }
        System.out.print(links.size());
//        try (PrintWriter out = new PrintWriter(new File("links.txt"))) {
//                    out.println(links.toString());
//        } 
    }

}
