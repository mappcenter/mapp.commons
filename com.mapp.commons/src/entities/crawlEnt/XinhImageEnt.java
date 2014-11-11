/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.crawlEnt;

import com.google.gson.JsonObject;

/**
 *
 * @author liempt
 */
public class XinhImageEnt {
    public String Thumb = "";
    public String Image = "";
    public String ImageLarge = "";
    
    public XinhImageEnt() {
        this.Thumb = "";
        this.Image = "";
        this.ImageLarge = "";
    }
    
    public XinhImageEnt(JsonObject jsonObject) {
        if(jsonObject!=null){            
            this.Thumb = jsonObject.get("thumb").getAsString();
            this.Image = jsonObject.get("Image").getAsString();
            this.ImageLarge = jsonObject.get("Image Large").getAsString();
        }
    }
    
    
}
