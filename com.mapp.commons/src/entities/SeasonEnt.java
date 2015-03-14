/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;

/**
 *
 * @author MrFlex
 */
public class SeasonEnt {
    public long Id;
    public String Name;
    public long ChannelId;
    public String Source;
    public int Status;
    public List<VideoEnt> ListVideos;
    
    public class STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 2;
        public static final int DELETED = 3;
        public static final int FULL = 4;
    }
}
