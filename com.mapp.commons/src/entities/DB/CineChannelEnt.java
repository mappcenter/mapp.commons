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
import serviceUtils.CategoryServiceUtils;

/**
 *
 * @author MrFlex
 */
public class CineChannelEnt {
    public long Id;
    public String Name;
    public String Image;
    public String Cover;
    public String Description;
    public String Country;
    public String Time;
    public List<Long> Categories;
    public List<String> Tags;
    public List<Long> ArtistIds;
    public int Status;
    public String Source;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
    }
    
    
    public CineChannelEnt(){
        this.Id = 0;
        this.Name = "";
        this.Image = "";
        this.Cover = "";
        this.Description = "";
        this.Country = "";
        this.Time = "";
        this.Categories = new ArrayList<Long>();
        this.Tags = new ArrayList<String>();
        this.ArtistIds = new ArrayList<Long>();
        this.Status = CineChannelEnt.STATUS.ENABLE;
        this.Source = "";
        
    }
    
    public CineChannelEnt(ZingTVShowEnt zingTVShowEnt, long parentId, String parentName) {
        if(zingTVShowEnt!=null){            
            this.Id = 0;
            this.Name = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Title);
            this.Image = zingTVShowEnt.Image;
            this.Cover = zingTVShowEnt.Cover;
            this.Description = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Description);
            this.Country = StringEscapeUtils.unescapeHtml(zingTVShowEnt.QuocGia);
            this.Time = "";
            this.Categories = CategoryServiceUtils.getListCategoryIds(zingTVShowEnt.Genres, "HDBox", parentId, parentName);
            
            this.Tags = zingTVShowEnt.Tags;
//            this.ArtistIds = zingTVShowEnt.Artist;
            this.ArtistIds = new ArrayList<Long>();
            this.ArtistIds.add(5L);
            this.Status = CineChannelEnt.STATUS.ENABLE;
            this.Source = zingTVShowEnt.Source;
        }
    }
    
 
}
