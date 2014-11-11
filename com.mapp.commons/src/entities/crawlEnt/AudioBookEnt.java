/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.crawlEnt;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import commonUtils.CommonUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liempt
 */
public class AudioBookEnt {
    public long TruyenID;
    public String TruyenName;
    public String Tacgia;
    public int SoPhan;
    public String Truyen_CoverImage;
    public List<String> LinkAudio;
    public String TheLoai_ID;
    public String TheLoaiName;
    public List<String> Tags;
    public int Status;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
    }
    
    public AudioBookEnt(JsonObject jsonObject, String tags, int status) {
        if(jsonObject!=null){            
            this.TruyenID = jsonObject.get("TruyenID").getAsLong();
            this.TruyenName = jsonObject.get("TruyenName").getAsString();
            this.Tacgia = jsonObject.get("Tacgia").getAsString();
            this.SoPhan = jsonObject.get("SoPhan").getAsInt();
            this.Truyen_CoverImage = jsonObject.get("Truyen_CoverImage").getAsString();
            this.LinkAudio = new ArrayList<String>();
            JsonArray jsonElementData = jsonObject.get("LinkAudio").getAsJsonArray();
            if(jsonElementData!=null && jsonElementData.size()>0){
                for(JsonElement tmpJson : jsonElementData){
                    String tmpItem = tmpJson.getAsString();
                    this.LinkAudio.add(tmpItem);
                }
            }
            this.TheLoai_ID = jsonObject.get("TheLoai_ID").getAsString();
            this.TheLoaiName = jsonObject.get("TheLoaiName").getAsString();
            this.Tags = CommonUtils.ParseStringToListString(tags);
            this.Status = status;
        }
    }
}
