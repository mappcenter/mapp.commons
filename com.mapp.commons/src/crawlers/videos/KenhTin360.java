/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.util.JSONUtil;
import commonUtils.CommonUtils;
import entities.crawlEnt.VideoLinkEnt;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author liempt
 */
public class KenhTin360 {
    
    public static void main(String[] args) {        
        String urlCrawl = "http://kenhtin360.com/category/video/page/%s/";
            
        try {
            for(int i=1;i<50;i++){
                String tmpX = String.format(urlCrawl, i);
                List<VideoLinkEnt> tmpVideoLinkEnt = getListVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
//                    CreateVideoLinks(tmpVideoLinkEnt);
                    String dateFiles = "";
                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
                        dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
                    }
                    files.FileUtils.WriteDatFile("/home/liempt/Desktop/KenhTin360.txt", dateFiles);
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
        } catch (Exception e) {
        }
        
    }
    
    public static List<VideoLinkEnt> getListVideoLinkEnt(String videoLink) {
        try {
            List<VideoLinkEnt> listResult = new ArrayList<VideoLinkEnt>();
            Document doc = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            
            Element tmpYoutubeTOP = doc.select("div.big_news_folder").select("a").first();
            if(tmpYoutubeTOP!=null){
                String tmpLink = tmpYoutubeTOP.attr("href");
                System.out.println(tmpLink);
                VideoLinkEnt tmpVideoLinkEnt = getVideoLinkEnt(tmpLink);
                listResult.add(tmpVideoLinkEnt);
            }
            Elements elementGenres = doc.select("ul.ul_folder").select("li");
            if(elementGenres!=null&&elementGenres.size()>0){
                for(Element tmpVideoLink : elementGenres){
                    Element tmpYoutube = tmpVideoLink.select("a").first();
                    if(tmpYoutube!=null){
                        String tmpLink = tmpYoutube.attr("href");
                        System.out.println(tmpLink);
                        VideoLinkEnt tmpVideoLinkEnt = getVideoLinkEnt(tmpLink);
                        listResult.add(tmpVideoLinkEnt);
                    }
                }
                return listResult;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    public static VideoLinkEnt getVideoLinkEnt(String videoLink) {
        try {
            VideoLinkEnt result = new VideoLinkEnt();
            Document doc = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            
            String tmpScript = doc.select("div.remain_detail").select("script").first().html();
            if(!commonUtils.CommonUtils.IsNullOrEmpty(tmpScript)){
                tmpScript = tmpScript.split("file:\'")[1];
                if(!CommonUtils.IsNullOrEmpty(tmpScript)){
                    tmpScript = tmpScript.split("\'")[0];
                }
                result.Link = tmpScript.replace("https://www.youtube.com/watch?v=", "");
                result.Source = videoLink;
                result.Status = 0;
                result.Title = doc.select("div.header_detail").select("h2.title_detail").text();
                
                return result;
            }
            
            
            
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}