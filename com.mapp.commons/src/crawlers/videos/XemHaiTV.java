/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.common.LogUtil;
import com.nct.framework.util.JSONUtil;
import commonUtils.CommonUtils;
import commonUtils.SendMailSSL;
import entities.crawlEnt.VideoLinkEnt;
import java.net.URLDecoder;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author liempt
 */
public class XemHaiTV {
    private static final Logger logger = LogUtil.getLogger(XemHaiTV.class);
    private static final SendMailSSL sendmail = SendMailSSL.getInstance();
    
    public static void main(String[] args) {        
        String urlWebTemplate = "http://xemhai.tv/clip-hai/?&cid=%s&start=%s";
        String result = "";
        String urlX = "http://xemhai.tv/clip-hai/%s/liveshow-viet-huong-kieu-oanh.html";
            
        try {
            
            
            
//            Document doc = Jsoup.connect(String.format(urlWebTemplate, 4, 0))
//                    .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
//                    .header("charset", "UTF-8")
//                    .timeout(600000)
//                    .get();
//            Elements listItem = doc.select("div.row-video").select("div.col-video");
//            if(listItem!=null && listItem.size()>0){
//                for(Element tmpElement : listItem){
//                    if(tmpElement!=null){
//                        result = tmpElement.select("a").attr("href");                        
//                        VideoLinkEnt tmpVideoLinkEnt = getVideoLinkEnt(result);
//                        System.out.println(JSONUtil.Serialize(tmpVideoLinkEnt));
//                    }
//                }
//            }
            
            
//            String channelName = doc.select("body").select("div.primary-header-contents").select("h1").select("span[dir=ltr]").select("a[dir=ltr]").text();
            for(int i=0;i<10000;i++){
                String tmpX = String.format(urlX, i);
                VideoLinkEnt tmpVideoLinkEnt = getVideoLinkEnt(tmpX);
                if(tmpVideoLinkEnt!=null){
                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
                }
            }
        } catch (Exception e) {
        }
        
    }
    
    public static VideoLinkEnt getVideoLinkEnt(String videoLink) {
        try {
            Document doc = Jsoup.connect(videoLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            String linkVideo = doc.select("iframe").attr("src");
            String linkVideoDecode = URLDecoder.decode(linkVideo, "UTF-8");
            String titleVideo = doc.select("meta[itemprop=contentURL]").attr("content");
            String htmlVideo = doc.select("div.viewVideo").html();
            
            
            if(!CommonUtils.IsNullOrEmpty(linkVideo)&&!CommonUtils.IsNullOrEmpty(titleVideo)){
                sendmail.sendHTML("socdientu.post2015@blogger.com", titleVideo, htmlVideo, null);
                
//                linkVideo = linkVideo.replaceAll("//www.youtube.com/embed/", "");
                VideoLinkEnt tmpVideoLinkEnt = new VideoLinkEnt();
                tmpVideoLinkEnt.Link = linkVideoDecode;
                tmpVideoLinkEnt.Source = "XemHaiTV";
                tmpVideoLinkEnt.Status = 0;
                tmpVideoLinkEnt.Title = titleVideo;
                return tmpVideoLinkEnt;
                
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
