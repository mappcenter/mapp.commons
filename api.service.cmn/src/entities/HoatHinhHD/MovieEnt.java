/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.HoatHinhHD;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MrFlex
 */
public class MovieEnt {
    public String Id = "";
    public String Name = "";
    public String Image = "";
    public String Cover = "";
    public String Description = "";
    public String Country = "";
    public String Count = "";
    public List<ChapterEnt> Chapters = new ArrayList<ChapterEnt>();
}
