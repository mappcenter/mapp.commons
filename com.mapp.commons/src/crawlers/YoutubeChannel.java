/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author liempt
 */
public class YoutubeChannel {
    public static String getChannelId(String channelLink) {
        if (channelLink == null || channelLink.isEmpty()) {
            return "";
        }
        String result = "";
        try {
            Document doc = Jsoup.connect(channelLink)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                    .header("charset", "UTF-8")
                    .timeout(600000)
                    .get();
            result = doc.select("head").select("meta[itemprop=channelId]").attr("content");
            String channelName = doc.select("body").select("div.primary-header-contents").select("h1").select("span[dir=ltr]").select("a[dir=ltr]").text();
            System.out.println(channelName);
        } catch (Exception e) {
        }

        return result;
    }
}
