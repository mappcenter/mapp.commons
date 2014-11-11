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
import com.nct.framework.util.JSONUtil;
import databaseUtils.DatabaseServiceUtils;
import entities.DB.AppImageEnt;
import entities.crawlEnt.XinhImageEnt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author liempt
 */
public class Xinh {
    private static final Logger logger = LogUtil.getLogger(Xinh.class);
    
    public static void main(String[] args) {
        System.out.println("Crawl Data Xinh!");
        logger.fatal("Crawl Data Xinh!");
        int pageSize = 120;
        String linkXinhCategory = "http://vietmkt.com/xinhxinh/?op=categories";
        String linkHome = "http://vietmkt.com/xinhxinh/?op=home&page=%s&pagesize=%s&device=ipad";
        String linkCategory = "http://vietmkt.com/xinhxinh/?op=getListCate&ID=%s&page=%s&pagesize=%s&device=ipad";
        
        try{
            //Get Home
            boolean isLoad = true;
            int i=0;
            while(isLoad){
                i = (i+1);
                System.out.println("[Home] Get Image {"+pageSize+"} -> "+ i);
                logger.fatal("[Home] Get Image {"+pageSize+"} -> "+ i);
                String urlHome = String.format(linkHome, i, pageSize);
                isLoad = saveImageByUrl(urlHome, "Xinh,XHome");
            }
            
            
            
            //Get All Category
            JsonObject jsonObjCategory = readJsonObjectFromUrl(linkXinhCategory);
            if (jsonObjCategory != null) {
                JsonElement jsonElementErrorCode = jsonObjCategory.get("ErrorCode");
                if (jsonElementErrorCode != null&&jsonElementErrorCode.getAsInt()==0){
                    JsonElement jsonElementLoadMore = jsonObjCategory.get("LoadMore");
                    JsonArray jsonElementData = jsonObjCategory.get("DATA").getAsJsonArray();
                    if(jsonElementData!=null && jsonElementData.size()>0){
                        for(JsonElement tmpJson :jsonElementData){
                            String catName = tmpJson.getAsJsonObject().get("NAME_CAT").getAsString();
                            String catKey = tmpJson.getAsJsonObject().get("link").getAsString();
                            
                            isLoad = true;
                            i=0;
                            while(isLoad){
                                i = (i+1);
                                System.out.println("["+catKey+"] Get Image {"+pageSize+"} -> "+ i);
                                logger.fatal("["+catKey+"] Get Image {"+pageSize+"} -> "+ i);
                                String urlCategory = String.format(linkCategory, catKey, i, pageSize);
                                isLoad = saveImageByUrl(urlCategory, "Xinh,"+catName+","+catKey);
                            }
                        }
                    }
                }
            }
            System.exit(0);
            
            
        
        
//            System.out.println(YoutubeChannel.getChannelId("https://www.youtube.com/channel/UC_iuTdGAVYHMcUZm-srQcRw"));
//            System.out.println(YoutubeChannel.getChannelId("https://www.youtube.com/user/trucnhanchannel"));
//            System.out.println(YoutubeChannel.getChannelId("https://www.youtube.com/channel/UC_iuTdGAVYHMcUZm-srQcRw!fdsafasd").length());
        }catch(Exception ex){
            
        }
    }
    
    private static boolean saveImageByUrl(String urlGet, String tags) throws IOException {
        boolean isMore = false;
        List<AppImageEnt> listAppImageEnt = new ArrayList<AppImageEnt>();
        JsonObject jsonObj = readJsonObjectFromUrl(urlGet);
        if (jsonObj != null) {
            JsonElement jsonElementErrorCode = jsonObj.get("ErrorCode");
            if (jsonElementErrorCode != null&&jsonElementErrorCode.getAsInt()==0){
                String jsonElementLoadMore = jsonObj.get("LoadMore").getAsString();
                if(jsonElementLoadMore.equalsIgnoreCase("YES")){
                    isMore = true;
                }
                
                JsonArray jsonElementData = jsonObj.get("DATA").getAsJsonArray();
                if(jsonElementData!=null && jsonElementData.size()>0){
                    for(JsonElement tmpJson :jsonElementData){
                        JsonObject jsonItem = tmpJson.getAsJsonObject();
                        listAppImageEnt.add(new AppImageEnt(jsonItem, tags, AppImageEnt.STATUS.ENABLE));
                    }
                }

                DatabaseServiceUtils.InsertAppImageEnt(listAppImageEnt);
            }
        }
        return isMore;
    }
    private static HashMap<String, JsonArray> readMapJsonFromUrl(List<String> listUrl) throws IOException {
        String listURLString = StringUtils.join(listUrl, ",");
        InputStream is = new URL("https://graph.facebook.com/comments/?ids=" + listURLString).openStream();
        HashMap<String, JsonArray> returnValue = new HashMap<String, JsonArray>();
        
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
                JsonObject jsonObj = jsonParser.parse(jsonText).getAsJsonObject();

                if (jsonObj != null) {
                    for(String tmpUrl : listUrl){
                        JsonElement jsonElement = jsonObj.get(tmpUrl);
                        JsonArray json = null;
                        if (jsonElement != null){
                            JsonElement jsonElementSub = jsonElement.getAsJsonObject().get("comments");
                            if(jsonElementSub!=null)
                                json = jsonElementSub.getAsJsonObject().get("data").getAsJsonArray();
                        }
                        
                        if(json!=null)
                            returnValue.put(tmpUrl, json);
                    }
                }
            }
            return returnValue;
        } finally {
            is.close();
        }
    }    
    
    private static JsonArray readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        JsonArray json = null;
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
                JsonObject jsonObj = jsonParser.parse(jsonText).getAsJsonObject();

                if (jsonObj != null) {
                    JsonElement jsonElement = jsonObj.get(url);
                    if (jsonElement != null){
                        JsonElement jsonElementSub = jsonElement.getAsJsonObject().get("comments");
                        if(jsonElementSub!=null)
                            json = jsonElementSub.getAsJsonObject().get("data").getAsJsonArray();
                    }
                }
            }
            return json;
        } finally {
            is.close();
        }
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
