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
public class AdvEnt {
    public String Image = "";
    public String Link = "";
    
    public AdvEnt(){
        Image = "";
        Link = "";
    }
    
    public AdvEnt(String advImage, String advLink){
        Image = advImage;
        Link = advLink;
    }
}
