/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.hoathinhhd.entities;

import java.util.List;

/**
 *
 * @author MrFlex
 */
public class VideoEnt {
    public long Id;
    public String Name;
    public String Image;
    public long MovieId;
    public List<Long> Categories;
    public int Status;
    public String Source;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
    }
}
