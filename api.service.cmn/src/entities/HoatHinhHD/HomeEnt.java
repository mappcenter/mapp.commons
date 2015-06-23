/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.HoatHinhHD;

import java.util.List;

/**
 *
 * @author MrFlex
 */
public class HomeEnt {
    public List<ShowcaseEnt> Showcase;
    public List<MovieEnt> ListData;
    
    public HomeEnt(List<ShowcaseEnt> appShowcase, List<MovieEnt> appListData){
        this.Showcase = appShowcase;
        this.ListData = appListData;
    }
}
