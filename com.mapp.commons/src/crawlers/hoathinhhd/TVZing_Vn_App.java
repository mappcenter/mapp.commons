/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.hoathinhhd;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nct.framework.util.ConvertUtils;
import commonUtils.CommonUtils;
import crawlers.hoathinhhd.entities.MovieEnt;
import crawlers.hoathinhhd.entities.QualityEnt;
import crawlers.hoathinhhd.entities.VideoEnt;
import databaseUtils.HoatHinhServiceUtils;
import entities.crawlEnt.ZingTVShowEnt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MrFlex
 */
public class TVZing_Vn_App {
    /*  
    
    http://image.mp3.zdn.vn/tv_program_210_210//b/c/bc4daf6b641d98381785f4e9777ae6ee_1377768288.jpg

    http://api.tv.zing.vn/3.0/program/list?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&count=20&genre_id=83&list_type=new&os=ios&os_version=8.3&page=1
    http://api.tv.zing.vn/3.0/program/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&os=ios&os_version=8.3&program_id=3491&session_key=id_760F59D9-D7FD-4948-8B76-C7D96B1B786A
    http://api.tv.zing.vn/3.0/series/medias?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&count=20&os=ios&os_version=8.3&page=1&series_id=5871&session_key=id_760F59D9-D7FD-4948-8B76-C7D96B1B786A
    http://api.tv.zing.vn/3.0/media/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&media_id=134696&os=ios&os_version=8.3&session_key=id_760F59D9-D7FD-4948-8B76-C7D96B1B786A
    http://tv10.r15s91.vcdn.vn/streaming/0da1a6cfe99ac4a43e4a660f57572bf0/557d963b/2015/0611/3c/5502cf5b0d0fa76bd4df173e8f35d282.mp4?format=f360&device=ios
    http://tv10.r15s91.vcdn.vn/streaming/0da1a6cfe99ac4a43e4a660f57572bf0/557d963b/2015/0611/3c/5502cf5b0d0fa76bd4df173e8f35d282.mp4?format=f360&device=ios


    http://api.tv.zing.vn/3.0/genre/child?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=1.3.1&genre_id=83&os=ios&os_version=6
    http://api.tv.zing.vn/3.0/program/list?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=1.3.1&count=20&genre_id=83&list_type=new&os=ios&os_version=6&page=1
    http://api.tv.zing.vn/3.0/program/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=1.3.1&os=ios&os_version=6&program_id=3491&session_key=id_1365F7C8-B33E-4596-81B0-DA16BC1B9897
    http://image.mp3.zdn.vn/tv_program_210_210//b/c/bc4daf6b641d98381785f4e9777ae6ee_1377768288.jpg
    http://api.tv.zing.vn/3.0/series/medias?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&count=20&page=1&series_id=5871&session_key=id_1365F7C8-B33E-4596-81B0-DA16BC1B9897
    http://api.tv.zing.vn/3.0/series/medias3?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=1.3.1&count=200&media_id=134694&os=ios&os_version=6&page=0
    http://api.tv.zing.vn/3.0/media/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=1.3.1&media_id=134694&os=ios&os_version=6&session_key=id_1365F7C8-B33E-4596-81B0-DA16BC1B9897
    
    
    */
    
    public static void main(String[] args) {
//        String urlTemplateShow = "http://tv.zing.vn/the-loai/Anime/IWZ9ZII0.html?sort=new&p=%s";
//        urlTemplateShow = "http://tv.zing.vn/the-loai/Hanh-Dong-Phieu-Luu/IWZ9ZI0B.html?sort=new&p=%s";
//        for(int i=1;i<12;i++){
//            String tmpX = String.format(urlTemplateShow, i);
//            List<String> listLinkShow = getListLinkShow(tmpX);
//            if(listLinkShow!=null&&listLinkShow.size()>0){
//                for(String tmpLinkShow : listLinkShow){
//                    ZingTVShowEnt tmpVideoLinkEnt = getZingTVShowEnt(tmpLinkShow);
//                    if(tmpVideoLinkEnt!=null){
//                        MovieEnt tmpChannelEnt = new MovieEnt(tmpVideoLinkEnt);
//                        long tmpId = HoatHinhServiceUtils.CreateMovie(tmpChannelEnt);
//                        System.out.println("Id: "+ tmpId + " | " + tmpLinkShow);
//                    }
//                }
//            }
//        }
        
        
//        //Read Videos For Movie
//        List<MovieEnt> listMovie = HoatHinhServiceUtils.GetListMovie(MovieEnt.STATUS.ENABLE);
//        if(listMovie!=null && listMovie.size()>0){
//            for(MovieEnt tmpMovie : listMovie){
//                if(tmpMovie!=null && !commonUtils.CommonUtils.IsNullOrEmpty(tmpMovie.Source)){
//                    List<VideoEnt> listVideo = getListVideo(tmpMovie.Id, tmpMovie.Source);
//                    if(listVideo!=null && listVideo.size()>0){
//                        long idResult = HoatHinhServiceUtils.CreateListVideo(listVideo);
//                        System.out.println("Id: "+ tmpMovie.Id + " | " + tmpMovie.Name + " | " + tmpMovie.Source + " | " + idResult);
//                    }
//                }
//            }
//            
//        }
        
        
        //Read Stream for video
        List<VideoEnt> listVideo = HoatHinhServiceUtils.GetListVideo(VideoEnt.STATUS.ENABLE);
        if(listVideo!=null && listVideo.size()>0){
            for(VideoEnt tmpVideo : listVideo){
                if(tmpVideo!=null && !commonUtils.CommonUtils.IsNullOrEmpty(tmpVideo.Source)){
                    List<QualityEnt> listStream = getListStream(tmpVideo.Id, tmpVideo.Source);
                    if(listStream!=null && listStream.size()>0){
                        long idResult = HoatHinhServiceUtils.CreateListStream(listStream);
                        System.out.println("Id: "+ tmpVideo.Id + " | " + tmpVideo.Name + " | " + tmpVideo.Source + " | " + idResult);
                    }
                }
            }
            
        }
//        List<QualityEnt> listStream = getListStream(137956); //133562);
        
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
                        String[] lstKey = StringUtils.split(tmpGenres.text(), "-");
                        if(lstKey!=null&&lstKey.length>0){
                            for(String tmpKeyGenre : lstKey){
                                showGenres.add(tmpKeyGenre.trim());
                            }
                        }
                        
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
    
    public static List<VideoEnt> getListVideo(long movieId, String showLink) {
        try {
            Document doc = Jsoup.connect(showLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            String showId = doc.select("div#_commentList").attr("oid");
            String urlGetSerial = "http://api.tv.zing.vn/3.0/program/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&os=ios&os_version=8.3&program_id="+showId;
            Document docGetSerial = Jsoup.connect(urlGetSerial)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .ignoreContentType(true)
                        .get();
            
            String dataResultSerial = docGetSerial.text();
            if(commonUtils.CommonUtils.IsNullOrEmpty(dataResultSerial)){
                return null;
            }
            
            List<VideoEnt> listVideoResult = new ArrayList<VideoEnt>();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(dataResultSerial).getAsJsonObject().get("response").getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("series").getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject item = element.getAsJsonObject();
                int idSerial = item.get("id").getAsInt();
                int totalVideo = item.get("total").getAsInt();
                
                //Get Video For Serial
                String urlGetVideo = "http://api.tv.zing.vn/3.0/series/medias?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&count="+totalVideo+"&os=ios&os_version=8.3&page=1&series_id="+idSerial+"&session_key=id_760F59D9-D7FD-4948-8B76-C7D96B1B786A";
                Document docurlGetVideo = Jsoup.connect(urlGetVideo)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .ignoreContentType(true)
                        .get();
                String dataResultVideo = docurlGetVideo.text();
                JsonArray jsonArrayVideo = parser.parse(dataResultVideo).getAsJsonObject().get("response").getAsJsonArray();
                for (JsonElement elementVideo : jsonArrayVideo) {
                    JsonObject itemVideo = elementVideo.getAsJsonObject();
                    VideoEnt tmpVideo = new VideoEnt();
                    tmpVideo.Name = itemVideo.get("full_name").getAsString();
                    tmpVideo.Image = "http://image.mp3.zdn.vn/tv_media_210_120/"+itemVideo.get("thumbnail").getAsString();
                    tmpVideo.MovieId = movieId;
                    tmpVideo.Status = VideoEnt.STATUS.ENABLE;
                    tmpVideo.Source = itemVideo.get("id").getAsString();
                    
                    listVideoResult.add(tmpVideo);
                }
                
            }
            return listVideoResult;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<QualityEnt> getListStream(long videoId, String videoSource) {
        List<QualityEnt> listReturn = new ArrayList<QualityEnt>();
        try {
            String streamLink = "http://api.tv.zing.vn/3.0/media/info?api_key=c6cdcfff42dd8cd70e7e4d3b58a162c2&app_version=2.0.2&media_id="+videoSource+"&os=ios&os_version=8.3&session_key=id_760F59D9-D7FD-4948-8B76-C7D96B1B786A";
            Document docStream = Jsoup.connect(streamLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .ignoreContentType(true)
                        .get();
            
            
            String streamContent = docStream.text();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(streamContent).getAsJsonObject().get("response").getAsJsonObject();
            int duration = jsonObject.get("duration").getAsInt();
            String treamLink360 = jsonObject.get("file_url").getAsString();
            if(!CommonUtils.IsNullOrEmpty(treamLink360)){
                QualityEnt tmpQualityEnt = new QualityEnt();
                tmpQualityEnt.Link = "http://"+treamLink360;
                tmpQualityEnt.Quality = "360";
                tmpQualityEnt.Source = ""+videoSource;
                tmpQualityEnt.Status = QualityEnt.STATUS.ENABLE;
                tmpQualityEnt.Type = "ZING";
                tmpQualityEnt.VideoId = videoId;
                listReturn.add(tmpQualityEnt);
            }
            
            
            JsonObject jsonOtherUrl = jsonObject.get("other_url").getAsJsonObject();
            if(jsonOtherUrl!=null && jsonOtherUrl.isJsonObject()){                
                String stream720 = jsonOtherUrl.get("Video720")!=null ? jsonOtherUrl.get("Video720").getAsString() : "";
                if(!CommonUtils.IsNullOrEmpty(stream720)){
                    QualityEnt tmpQualityEnt = new QualityEnt();
                    tmpQualityEnt.Link = "http://"+stream720;
                    tmpQualityEnt.Quality = "720";
                    tmpQualityEnt.Source = ""+videoSource;
                    tmpQualityEnt.Status = QualityEnt.STATUS.ENABLE;
                    tmpQualityEnt.Type = "ZING";
                    tmpQualityEnt.VideoId = videoId;
                    listReturn.add(tmpQualityEnt);
                }
                String stream480 = jsonOtherUrl.get("Video480")!=null ? jsonOtherUrl.get("Video480").getAsString() : "";
                if(!CommonUtils.IsNullOrEmpty(stream480)){
                    QualityEnt tmpQualityEnt = new QualityEnt();
                    tmpQualityEnt.Link = "http://"+stream480;
                    tmpQualityEnt.Quality = "480";
                    tmpQualityEnt.Source = ""+videoSource;
                    tmpQualityEnt.Status = QualityEnt.STATUS.ENABLE;
                    tmpQualityEnt.Type = "ZING";
                    tmpQualityEnt.VideoId = videoId;
                    listReturn.add(tmpQualityEnt);
                }
                
            }
            
            if(listReturn.size()>0){
                return listReturn;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
