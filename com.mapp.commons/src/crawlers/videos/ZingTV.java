/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.util.JSONUtil;
import commonUtils.CommonUtils;
import static crawlers.videos.XemHaiTV.getVideoLinkEnt;
import entities.crawlEnt.VideoLinkEnt;
import entities.crawlEnt.ZingTVShowEnt;
import entities.crawlEnt.ZingTVVideoEnt;
import java.net.URLDecoder;
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
public class ZingTV {
    
    public static void main(String[] args) {        
        String urlShow = "http://tv.zing.vn/the-100-season-2";
        ZingTVShowEnt tmpVideoLinkEnt = getZingTVShowEnt(urlShow);
        if(tmpVideoLinkEnt!=null){
            System.out.println("URL:"+urlShow+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
        }
        
        
        urlShow = "http://tv.zing.vn/video/The-100-Season-2-Tap-2-Inclement-Weather/IWZACA8A.html";
        ZingTVVideoEnt tmpVideo = getZingTVVideoEnt(urlShow);
        if(tmpVideo!=null){
            System.out.println("URL:"+urlShow+" => "+JSONUtil.Serialize(tmpVideo));
        }
    }
    
    
    
    
    public static ZingTVShowEnt getZingTVShowEnt(String showLink) {
        try {
            Document doc = Jsoup.connect(showLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            String showTitle = doc.select("div.box-description").select("h3").html();
            String showImage = doc.select("div.outside-des").select("img").attr("src");
            String showCover = doc.select("div.fluid-img").select("img").attr("src");
            List<String> showGenres = new ArrayList<String>();
            Elements elementGenres = doc.select("div.tag").first().select("a");
            if(elementGenres!=null&&elementGenres.size()>0){
                for(Element tmpGenres : elementGenres){
                    if(tmpGenres!=null){
                        showGenres.add(tmpGenres.html());
                    }
                }
            }
            String showDescription = doc.getElementById("_description").html();
            List<String> showTags = new ArrayList<String>();
            Elements elementTags = doc.select("div.tag").last().select("a");
            if(elementTags!=null&&elementTags.size()>0){
                for(Element tmpTags : elementTags){
                    if(tmpTags!=null){
                        showTags.add(tmpTags.html());
                    }
                }
            }
            String showQuocGia = doc.select("div.box-statistics").select("ul").select("li").first().html().replaceAll("<strong>Quốc gia: </strong>", "");
            
            String showArtis = doc.select("div._artistdata").attr("id");
            //http://tv.zing.vn/tv/ajaxtv/artist-v2/get-artist?objectType=profile&objectId=IWZ9ZCFU_32715_37386_17793&callback=zmCore.js573587
            

            List<String> showVideos = new ArrayList<String>();
            Elements elementVideo = doc.select("div.subtray.flex-des");
            if(elementVideo!=null&&elementVideo.size()>0){
                for(Element tmpVideo : elementVideo){
                    if(tmpVideo!=null){
                        showVideos.add("http://tv.zing.vn"+tmpVideo.select("a").first().attr("href"));
                    }
                }
            }
            
            if(!CommonUtils.IsNullOrEmpty(showTitle)&&!CommonUtils.IsNullOrEmpty(showImage)){
                ZingTVShowEnt tmpVideoLinkEnt = new ZingTVShowEnt();
                tmpVideoLinkEnt.Title = showTitle;
                tmpVideoLinkEnt.Image = showImage;
                tmpVideoLinkEnt.Cover = showCover;
                tmpVideoLinkEnt.Genres = showGenres;
                tmpVideoLinkEnt.Tags = showTags;
                tmpVideoLinkEnt.Description = showDescription;
                tmpVideoLinkEnt.QuocGia = showQuocGia;
                tmpVideoLinkEnt.Artist = showArtis;
                tmpVideoLinkEnt.Videos = showVideos;
                tmpVideoLinkEnt.Source = showLink;
                tmpVideoLinkEnt.Status = 0;
                return tmpVideoLinkEnt;
                
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    public static ZingTVVideoEnt getZingTVVideoEnt(String videoLink) {
        try {
            Document doc = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            String videoTitle = doc.select("div.box-description").select("h3").html();
            String videoImage = doc.select("meta[property=og:image]").attr("content");
            String videoStream = doc.getElementsContainingText("document.write('<source").html();
            String videoReleasedate = doc.select("div.box-description").select("ul").select("li").get(2).html().replaceAll("<strong>Ng&agrave;y ph&aacute;t h&agrave;nh: </strong>", "");
            
            String tmpStream = doc.select("div._insideBackground").html();
            if(!CommonUtils.IsNullOrEmpty(tmpStream)){
                tmpStream = tmpStream.split("document.write\\(\\'\\<source src\\=\"")[1];
                if(!CommonUtils.IsNullOrEmpty(tmpStream)){
                    tmpStream = tmpStream.split("\"")[0];
                    videoStream = tmpStream;
                }
            }
            if(!CommonUtils.IsNullOrEmpty(videoTitle)&&!CommonUtils.IsNullOrEmpty(videoImage)){
                ZingTVVideoEnt tmpVideoEnt = new ZingTVVideoEnt();
                tmpVideoEnt.Title = videoTitle;
                tmpVideoEnt.Image = videoImage;
                tmpVideoEnt.Stream = videoStream;
                tmpVideoEnt.Releasedate = videoReleasedate;
                tmpVideoEnt.Source = videoLink;
                return tmpVideoEnt;
                
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
            
}
