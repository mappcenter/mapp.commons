/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.crawlEnt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liempt
 */
public class ZingTVShowEnt {
    public String Title = "";
    public String Image = "";
    public String Cover = "";
    public List<String> Genres;
    public List<String> Tags;
    public String Description = "";
    public String QuocGia = "";
    public String Artist = "";
    public List<String> Videos;
    public String Source = "";
    public int Status = 0;
    
    public ZingTVShowEnt() {
        this.Title = "";
        this.Image = "";
        this.Cover = "";
        this.Genres = new ArrayList<String>();
        this.Tags = new ArrayList<String>();
        this.Description = "";
        this.QuocGia = "";
        this.Artist = "";
        this.Videos = new ArrayList<String>();
        this.Source = "";
        this.Status = 0;
    }
}
