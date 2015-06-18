/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.common.Config;
import com.nct.framework.common.LogUtil;
import com.nct.framework.util.ConvertUtils;
import com.nct.framework.util.JSONUtil;
import commonUtils.CommonUtils;
import entities.crawlEnt.VideoLinkEnt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author liempt
 */
public class Hai24h_TV { 
    private static final String configName = "main_settings";
    private static final String SAVE_FOLDER = ConvertUtils.toString(Config.getParam(configName, "folder"), "");
    private static final Logger logger = LogUtil.getLogger(Hai24h_TV.class);
    private static final String filePath = SAVE_FOLDER + "Hai24h_TV.txt";
    
    public static void main(String[] args) {        
        String urlHot = "http://hai24h.tv/categories/video-hot/page/%s/";
        String urlNew = "http://hai24h.tv/categories/video-moi-2/page/%s/";
            
        try {
            String dateFiles = "";
            //For HOT
            for(int i=1;i<10;i++){
                String tmpX = String.format(urlHot, i);
                List<VideoLinkEnt> tmpVideoLinkEnt = getListVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
                        if(xVideo!=null){
                            dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
                        }
                    }
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
            //For New
            for(int i=1;i<10;i++){
                String tmpX = String.format(urlNew, i);
                List<VideoLinkEnt> tmpVideoLinkEnt = getListVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
                        if(xVideo!=null){
                            dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
                        }
                    }
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
            
            //Write to Files
            File file = new File(filePath); 
            if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
            }else{
                    System.out.println("Delete operation is failed.");
            }
            System.out.println("Save File : "+filePath);
            files.FileUtils.WriteDatFile(filePath, dateFiles);
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        
    }
    
    public static String getVideoMain() {
        String urlHot = "http://hai24h.tv/categories/video-hot/page/%s/";
        String urlNew = "http://hai24h.tv/categories/video-moi-2/page/%s/";
        String dateFiles = "";
        
        try {
            //For HOT
            for(int i=1;i<10;i++){
                String tmpX = String.format(urlHot, i);
                List<VideoLinkEnt> tmpVideoLinkEnt = getListVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
                        if(xVideo!=null){
                            dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
                        }
                    }
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
            //For New
            for(int i=1;i<10;i++){
                String tmpX = String.format(urlNew, i);
                List<VideoLinkEnt> tmpVideoLinkEnt = getListVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
                        if(xVideo!=null){
                            dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
                        }
                    }
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
            //Write to Files
            File file = new File(filePath); 
            if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
            }else{
                    System.out.println("Delete operation is failed.");
            }
            files.FileUtils.WriteDatFile(filePath, dateFiles);
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        return dateFiles;
    }
    
    
     public static List<VideoLinkEnt> getListVideoLinkEnt(String videoLink) {
        try {
            List<VideoLinkEnt> listResult = new ArrayList<VideoLinkEnt>();
            Connection.Response response = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .execute();

            int statusCode = response.statusCode();
            if(statusCode == 200) {
                
                Document doc = Jsoup.connect(videoLink)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                            .header("charset", "UTF-8")
                            .timeout(600000)
                            .get();


                Elements elementVideo = doc.select("div.item-img");
                if(elementVideo!=null&&elementVideo.size()>0){
                    for(Element tmpVideoLink : elementVideo){
                        Element tmpYoutube = tmpVideoLink.select("a").first();
                        if(tmpYoutube!=null){
                            String tmpLink = tmpYoutube.attr("href");
                            VideoLinkEnt tmpVideoLinkEnt = getVideoLinkEnt(tmpLink);
                            listResult.add(tmpVideoLinkEnt);
                        }
                    }
                    return listResult;
                }
            }else{
                System.out.println("404: "+videoLink);
            }
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
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
            
            String tmpLink = doc.select("iframe").attr("src");
            if(!commonUtils.CommonUtils.IsNullOrEmpty(tmpLink)){
                tmpLink = tmpLink.split("https://www.youtube.com/embed/")[1];
                if(!CommonUtils.IsNullOrEmpty(tmpLink)){
                    tmpLink = tmpLink.split("\\?")[0];
                }
                
                result.Link = tmpLink;
                result.Source = videoLink;
                result.Status = 0;
                result.Title = doc.select("div.video-info").select("h1").text();
                
                return result;
            }
            
            
            
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
            return null;
        }
        return null;
    }
}
