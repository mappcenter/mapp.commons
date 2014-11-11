/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.DB;

import com.google.gson.JsonObject;
import commonUtils.CommonUtils;
import java.util.List;

/**
 *
 * @author liempt
 */
public class AppImageEnt {
    public long Id;
    public String Thumb;
    public String Normal;
    public String Full;
    public List<String> Tags;
    public int Status;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
    }
    
    public AppImageEnt(JsonObject jsonObject, String tags, int status) {
        if(jsonObject!=null){            
            this.Thumb = jsonObject.get("thumb").getAsString();
            this.Normal = jsonObject.get("Image").getAsString();
            this.Full = jsonObject.get("Image Large").getAsString();
            this.Tags = CommonUtils.ParseStringToListString(tags);
            this.Status = status;
        }
    }
}
