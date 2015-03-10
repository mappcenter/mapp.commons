/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.DB;

import entities.crawlEnt.ZingTVShowEnt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author MrFlex
 */
public class CineChannelEnt {
    public long Id;
    public String Name;
    public String Thumb;
    public String Cover;
    public String Description;
    public String Country;
    public String Time;
    public List<Long> Categories;
    public List<String> Tags;
    public List<Long> ArtistIds;
    public int Status;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
    }
    
    public CineChannelEnt(ZingTVShowEnt zingTVShowEnt) {
        if(zingTVShowEnt!=null){            
            this.Id = 0;
            this.Name = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Title);
            this.Thumb = zingTVShowEnt.Image;
            this.Cover = zingTVShowEnt.Cover;
            this.Description = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Description);
            this.Country = StringEscapeUtils.unescapeHtml(zingTVShowEnt.QuocGia);
            this.Time = "";
//            this.Categories = zingTVShowEnt.Genres;
            this.Categories = new ArrayList<Long>();
            this.Categories.add(1L);
            
            this.Tags = zingTVShowEnt.Tags;
//            this.ArtistIds = zingTVShowEnt.Artist;
            this.ArtistIds = new ArrayList<Long>();
            this.ArtistIds.add(5L);
            this.Status = CineChannelEnt.STATUS.ENABLE;
        }
    }
}
