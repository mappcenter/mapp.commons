/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.HoatHinhHD;

/**
 *
 * @author MrFlex
 */
public class RelateApp {
    public String Name = "";
    public String Des = "";
    public String Image = "";
    public String Link = "";
    
    public RelateApp(){
        Name = "";
        Des = "";
        Image = "";
        Link = "";
    }
    
    public RelateApp(String appName, String appDesc, String appImage, String appLink){
        Name = appName;
        Des = appDesc;
        Image = appImage;
        Link = appLink;
    }
}
