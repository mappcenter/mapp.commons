/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.congnghe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author liempt
 */
public class BinhLL_Com {
    public static void main(String[] args) {
        String urlTemplate = "http://binhll.com/detail_content.php?id=%s";
        
        for(int i=1;i<200;i++){
            String tmpX = String.format(urlTemplate, i);
            ContentEnt tmpContentEnt = getContent(tmpX);
            if(tmpContentEnt!=null){
                System.out.println("Id: "+ i + " | " + tmpX + " | " + tmpContentEnt.Title);
            }else{
                System.out.println("Id: "+ i + " | " + tmpX + " | NULL");
            }
            
        }
    }
    
    public static ContentEnt getContent(String pageLink) {
        ContentEnt listReturn = new ContentEnt();
        try {
            Document doc = Jsoup.connect(pageLink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1")
                        .header("charset", "UTF-8")
                        .timeout(600000)
                        .get();
            
            
            String showTitle = doc.select("div.container").select("div.heading").text();
            String showContent = doc.select("div.container").select("div.feature").first().select("div.body").html();
            if(!commonUtils.CommonUtils.IsNullOrEmpty(showTitle)&&!commonUtils.CommonUtils.IsNullOrEmpty(showContent)){
                listReturn.Title = showTitle;
                listReturn.Detail = showContent;
                return listReturn;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public static class ContentEnt {
        public String Title = "";
        public String Image = "";
        public String Detail = "";
        
        public ContentEnt() {
            this.Title = "";
            this.Image = "";
            this.Detail = "";
        }
    }

}
