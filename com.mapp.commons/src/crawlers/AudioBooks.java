/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nct.framework.common.LogUtil;
import databaseUtils.DatabaseServiceUtils;
import entities.DB.AppImageEnt;
import entities.crawlEnt.AudioBookEnt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author liempt
 */
public class AudioBooks {
    private static final Logger logger = LogUtil.getLogger(Xinh.class);
    
    public static void main(String[] args) {
        System.out.println("Crawl Data Audio Books!");
        logger.fatal("Crawl Data Audio Books!");
        
        String linkCategory = "http://clip.bhmedia.vn/api/audio?catid=%s";
        String linkDetail = "http://clip.bhmedia.vn/api/audio_detail?id=%s";
        
        try{
            for(int i=1;i<1000;i++){
                String urlHome = String.format(linkCategory, i);
                int numItems = saveAudioBookByUrl(urlHome, "AudioBook");
                System.out.println("[Category] Get Audio Books {"+i+"} -> "+ numItems);                    
                if(numItems>0){
                    logger.fatal("[Category] Get Audio Books {"+i+"} -> "+ numItems);
                }
            }
            
            System.exit(0);
        }catch(Exception ex){
            
        }
    }
    
    
    
    private static int saveAudioBookByUrl(String urlGet, String tags) throws IOException {
        int numItems = 0;
        List<AudioBookEnt> listAudioBookEnt = new ArrayList<AudioBookEnt>();
        JsonObject jsonObj = readJsonObjectFromUrl(urlGet);
        if (jsonObj != null) {
            JsonElement jsonElementErrorCode = jsonObj.get("status");
            if (jsonElementErrorCode != null&&jsonElementErrorCode.getAsInt()==1){
                int totalChapter = jsonObj.get("total").getAsInt();
                
                JsonArray jsonElementData = jsonObj.get("result").getAsJsonArray();
                if(jsonElementData!=null && jsonElementData.size()>0){
                    numItems = jsonElementData.size();
                    for(JsonElement tmpJson :jsonElementData){
                        JsonObject jsonItem = tmpJson.getAsJsonObject();
                        listAudioBookEnt.add(new AudioBookEnt(jsonItem, tags, AudioBookEnt.STATUS.ENABLE));
                    }
                }
                
//                DatabaseServiceUtils.InsertAppAudioBookEnt(listAudioBookEnt);
            }
        }
        return numItems;
    }
    
    private static JsonObject readJsonObjectFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        JsonObject jsonObj = null;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            if (jsonText != null && jsonText.length() > 0) {
                JsonParser jsonParser = new JsonParser();
                jsonObj = jsonParser.parse(jsonText).getAsJsonObject();

                if (jsonObj != null) {
                    return jsonObj;
                }
            }
            return jsonObj;
        } finally {
            is.close();
        }
    }
}
