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
import entities.crawlEnt.VideoLinkEnt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MrFlex
 */
public class Kenh101_Vn {
    private static final String configName = "main_settings";
    private static final String SAVE_FOLDER = ConvertUtils.toString(Config.getParam(configName, "folder"), "");
    private static final Logger logger = LogUtil.getLogger(Kenh101_Vn.class);
    private static final String filePath = SAVE_FOLDER + "Kenh101_Vn.txt";
    
    
    
    public static void main(String[] args) {        
        String urlHot = "http://xabuon.com/home/%s";//0->15->30
        //http://xabuon.com/get_list_news ======= http://xabuon.com/content/3036-LoL-.-Top-5-pha-ngo-ngan.htm
        
        try {
            String dateFiles = "";
            //For Home
            for(int i=0;i<20;i++){
                String tmpX = String.format(urlHot, (i*15));
                List<VideoLinkEnt> tmpVideoLinkEnt = getVideoLinkEnt(tmpX);
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
        
    }
    
    public static List<VideoLinkEnt> getVideoLinkEnt(String videoLink) {
        try {
            List<VideoLinkEnt> listResult = new ArrayList<VideoLinkEnt>();
            Document doc = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            
            
            Elements elementVideos = doc.select("ul.col-1").select("div.list").select("li.gag-link");
            if(elementVideos!=null&&elementVideos.size()>0){
                for(Element tmpVideoYoutube : elementVideos){
                    Element tmpYoutube = tmpVideoYoutube.select("img.thumb-img").first();
                    if(tmpYoutube!=null){
                        VideoLinkEnt tmpVideoLinkEnt = new VideoLinkEnt();
                        String tmpLink = tmpYoutube.attr("src");
                        if(tmpLink.contains("img.youtube.com")){
                            tmpLink = tmpLink.replaceAll("http:\\/\\/img.youtube.com\\/vi\\/", "");
                            tmpLink = tmpLink.split("/")[0];
                            tmpVideoLinkEnt.Link = tmpLink;
                            tmpVideoLinkEnt.Source = tmpVideoYoutube.attr("data-url");
                            tmpVideoLinkEnt.Status = 0;
                            tmpVideoLinkEnt.Title = tmpYoutube.attr("alt");
                            listResult.add(tmpVideoLinkEnt);
                        }
                    }
                }
                return listResult;
            }
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
            return null;
        }
        return null;
    }
}
