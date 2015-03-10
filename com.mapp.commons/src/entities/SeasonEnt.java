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
}
