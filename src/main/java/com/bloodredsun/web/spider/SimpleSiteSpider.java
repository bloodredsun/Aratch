package com.bloodredsun.web.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * A simple spider thrown together to test the pages on a site. It is limited to crawling the pages on a single site
 * and makes no promises about whether it has already read that page or not so cyclic references will result in this
 * running forever.
 */

public class SimpleSiteSpider {

    private static LinkedList<String> urls = new LinkedList<String>();

    private static String base = "http://beta.betfair.com";

    static {
        urls.add(base);
    }

    public static void main(String[] args) throws IOException {
        SimpleSiteSpider spider = new SimpleSiteSpider();
        spider.process(urls);
    }

    public void process(LinkedList<String> urls) {
        //Get the next one
        String nextUrl = urls.pop();

        //get content
        Document doc = null;
        try {
            long t0 = System.currentTimeMillis();
            doc = getContent(nextUrl);
            long t1 = System.currentTimeMillis();
            System.out.println(nextUrl + ", " + (doc.html().length() / 1000) + "k in " + ((t1 - t0) / 1000.0) + " seconds");
        } catch (IOException e) {
            System.out.println("Bad url");
        }

        //parse out the a tags
        Elements links = doc.select("a");

        //get all the href values of the a tags in a List
        List<String> urlList = new ArrayList<String>();
        for (Element link : links) {
            urlList.add(link.attr("href"));
        }
        //add base to absolute links
        for (ListIterator<String> urlIter = urlList.listIterator(); urlIter.hasNext(); ) {
            String url = urlIter.next();
            if (url.startsWith("/")) {
                urlIter.remove();
                urlIter.add(base + url);
            }

        }
        //remove non valid endpoints
        for (ListIterator<String> urlIter = urlList.listIterator(); urlIter.hasNext(); ) {
            String url = urlIter.next();
            if (url == null) {
                urlIter.remove();
            } else if (!url.startsWith(base)) {
                urlIter.remove();
            }
        }
        for (String url : urlList) {
            urls.add(url);
        }
        process(urls);

        System.out.println("done");
    }

    protected Document getContent(String nextUrl) throws IOException {
        return Jsoup.connect(nextUrl).get();
    }


}

