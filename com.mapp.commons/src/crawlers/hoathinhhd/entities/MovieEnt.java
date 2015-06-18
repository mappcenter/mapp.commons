/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.hoathinhhd.entities;

import commonUtils.CommonUtils;
import databaseUtils.HoatHinhServiceUtils;
import entities.CategoryEnt;
import entities.crawlEnt.ZingTVShowEnt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author MrFlex
 */
public class MovieEnt {
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
    
    
    public MovieEnt(){
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
        this.Status = MovieEnt.STATUS.ENABLE;
        this.Source = "";
        
    }
    
    public MovieEnt(ZingTVShowEnt zingTVShowEnt) {
        if(zingTVShowEnt!=null){            
            this.Id = 0;
            this.Name = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Title);
            this.Image = zingTVShowEnt.Image;
            this.Cover = zingTVShowEnt.Cover;
            this.Description = StringEscapeUtils.unescapeHtml(zingTVShowEnt.Description);
            this.Country = StringEscapeUtils.unescapeHtml(zingTVShowEnt.QuocGia);
            this.Time = "";
            this.Categories = getListCategoryIds(zingTVShowEnt.Genres, "HoatHinhHD");
            
            this.Tags = zingTVShowEnt.Tags;
//            this.ArtistIds = zingTVShowEnt.Artist;
            this.ArtistIds = new ArrayList<Long>();
            this.ArtistIds.add(5L);
            this.Status = MovieEnt.STATUS.ENABLE;
            this.Source = zingTVShowEnt.Source;
        }
    }
    
    public static List<Long> getListCategoryIds(List<String> categories, String catModule) {
        List result = new ArrayList();
        if ((categories == null) || (categories.isEmpty())) {
            return result;
        }
        for (String tmpCategory : categories) {
          if (!CommonUtils.IsNullOrEmpty(tmpCategory)) {
            long tmpCategoryId = HoatHinhServiceUtils.CheckExistNameWithParent(0, tmpCategory);
            if (tmpCategoryId > 0) {
                result.add(tmpCategoryId);
            } else {
                CategoryEnt tmpCategoryEnt = new CategoryEnt();
                tmpCategoryEnt.Module = catModule;
                tmpCategoryEnt.Name = tmpCategory;
                tmpCategoryEnt.Parent = 0;
                tmpCategoryEnt.Tree = tmpCategory;
                tmpCategoryId = HoatHinhServiceUtils.CreateCategory(tmpCategoryEnt);
                if (tmpCategoryId > 0) {
                  result.add(tmpCategoryId);
                }
            }
          }
        }
     return result;
    }
}
