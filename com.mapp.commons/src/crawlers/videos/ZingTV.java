/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.util.ConvertUtils;
import com.nct.framework.util.JSONUtil;
import commonUtils.CommonUtils;
import databaseUtils.CineServiceUtils;
import entities.DB.CineChannelEnt;
import entities.SeasonEnt;
import entities.VideoEnt;
import entities.crawlEnt.ZingTVShowEnt;
import entities.crawlEnt.ZingTVVideoEnt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
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
//        List<SeasonEnt> lstSeasonTest = getListSeasonEnt(6, "http://tv.zing.vn/cau-chuyen-cafe");
//        System.out.println(JSONUtil.Serialize(lstSeasonTest));
//        System.exit(0);
//        
        
        List<CineChannelEnt> listChannels = CineServiceUtils.GetListChannels(CineChannelEnt.STATUS.ENABLE);
        if(listChannels!=null&&listChannels.size()>0){
            for(CineChannelEnt tmpChannel : listChannels){
                if(tmpChannel!=null&&!CommonUtils.IsNullOrEmpty(tmpChannel.Source)){
                    List<SeasonEnt> lstSeason = getListSeasonEnt(tmpChannel.Id, tmpChannel.Source);
                    if(lstSeason!=null&&lstSeason.size()>0){
//                        CineServiceUtils.InsertSeasonEnt(lstSeason);
                        
                        for(SeasonEnt tmpSeason : lstSeason){
                            CineServiceUtils.CreateSeason(tmpSeason);
                        }
                    }
                }
            }
        }
        
        System.exit(0);
        
        
//        String urlTemplateShow = "http://tv.zing.vn/the-loai/Show-Viet-Nam/IWZ9ZII7.html?sort=new&p=%s";
//        String urlTemplateShow = "http://tv.zing.vn/the-loai/Show-Au-My/IWZ9ZII9.html?sort=new&p=%s";
        String urlTemplateShow = "http://tv.zing.vn/the-loai/Show-Thuc-Te/IWZ9ZIIU.html?sort=new&p=%s";
        long categoryParentId = 3;
        String categoryParentName = "TV Show";
        for(int i=1;i<12;i++){
            String tmpX = String.format(urlTemplateShow, i);
            List<String> listLinkShow = getListLinkShow(tmpX);
            if(listLinkShow!=null&&listLinkShow.size()>0){
                for(String tmpLinkShow : listLinkShow){
                    ZingTVShowEnt tmpVideoLinkEnt = getZingTVShowEnt(tmpLinkShow);
                    if(tmpVideoLinkEnt!=null){
                        CineChannelEnt tmpChannelEnt = new CineChannelEnt(tmpVideoLinkEnt, categoryParentId, categoryParentName);
                        long tmpId = CineServiceUtils.CreateCineChanel(tmpChannelEnt);
                        System.out.println("Id: "+ tmpId + " | " + tmpLinkShow);
                    }
                }
            }
        }
        
        
        
//        String urlShow = "http://tv.zing.vn/the-100-season-2";
//        ZingTVShowEnt tmpVideoLinkEnt = getZingTVShowEnt(urlShow);
//        if(tmpVideoLinkEnt!=null){
//            System.out.println("URL:"+urlShow+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
//            CineChannelEnt tmpChannelEnt = new CineChannelEnt(tmpVideoLinkEnt);
//            long tmpId = CineServiceUtils.CreateCineChanel(tmpChannelEnt);
//        }
//        
//        
//        urlShow = "http://tv.zing.vn/video/The-100-Season-2-Tap-2-Inclement-Weather/IWZACA8A.html";
//        ZingTVVideoEnt tmpVideo = getZingTVVideoEnt(urlShow);
//        if(tmpVideo!=null){
//            System.out.println("URL:"+urlShow+" => "+JSONUtil.Serialize(tmpVideo));
//        }
    }
    
    
    public static List<String> getListLinkShow(String showPageLink) {
        List<String> listReturn = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect(showPageLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            Elements elementShow = doc.select("div.item");
            if(elementShow!=null&&elementShow.size()>0){
                for(Element tmpShow : elementShow){
                    String tmpLink = tmpShow.select("a.thumb").attr("href");
                    if(!commonUtils.CommonUtils.IsNullOrEmpty(tmpLink)){
                        listReturn.add("http://tv.zing.vn"+tmpLink);
                    }
                }
                return listReturn;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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
                        showGenres.add(tmpGenres.text());
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
            
    public static List<SeasonEnt> getListSeasonEnt(long channelId, String showPageLink) {
        List<SeasonEnt> listReturn = new ArrayList<SeasonEnt>();
        try {
            Document doc = Jsoup.connect(showPageLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            Elements elementSeason = doc.select("div.row126");
            if(elementSeason!=null&&elementSeason.size()>0){
                for(Element tmpSeason : elementSeason){
                    SeasonEnt tmpSeasonEnt = new SeasonEnt();
                    tmpSeasonEnt.ChannelId = channelId;
                    String seasonTitle = tmpSeason.select("div.title-bar").select("h3>a").text();
                    String seasonSource = "http://tv.zing.vn"+tmpSeason.select("div.title-bar").select("h3>a").attr("href");
                    if(CommonUtils.IsNullOrEmpty(seasonTitle)){
                        seasonTitle = tmpSeason.select("div.title-bar").select("h3").text();
                        seasonSource = "";
                    }
                    tmpSeasonEnt.Name = seasonTitle;
                    tmpSeasonEnt.Source = seasonSource;
                    String strNumVideo = (tmpSeason.select("div.title-bar").select("div.see-all").select("a").last()!=null)?tmpSeason.select("div.title-bar").select("div.see-all").select("a").last().text():"";
                    
                    int numVideo = CommonUtils.IsNullOrEmpty(strNumVideo)? 0 : ConvertUtils.toInt(strNumVideo.replaceAll("\\)", "").replaceAll("Xem tất cả \\(", ""));
                    tmpSeasonEnt.Status = SeasonEnt.STATUS.FULL;
                    
                    Elements elementVideo = tmpSeason.select("div.flex-des");
                    if(elementVideo!=null&&elementVideo.size()>0){
                        tmpSeasonEnt.ListVideos = new ArrayList<VideoEnt>();
                        for(Element tmpVideo : elementVideo){
                            VideoEnt tmpVideoEnt = new VideoEnt();
                            tmpVideoEnt.Image = tmpVideo.select("img").attr("_src");
                            tmpVideoEnt.SeasonId = 0;
                            tmpVideoEnt.Source = "http://tv.zing.vn"+tmpVideo.select("a").first().attr("href");;
                            tmpVideoEnt.Status = 0;
                            tmpVideoEnt.Title = tmpVideo.select("img").attr("alt");
                            
                            tmpSeasonEnt.ListVideos.add(tmpVideoEnt);
                        }
                    }
                    if(numVideo>0&&numVideo>tmpSeasonEnt.ListVideos.size()){
                        tmpSeasonEnt.Status = SeasonEnt.STATUS.ENABLE;
                    }
                    listReturn.add(tmpSeasonEnt);
                }
                return listReturn;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
