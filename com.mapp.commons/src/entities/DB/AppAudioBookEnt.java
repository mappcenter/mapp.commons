/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.DB;

import java.util.List;

/**
 *
 * @author liempt
 */
public class AppAudioBookEnt {
    public long Id;
    public String Name;
    public String TacGia;
    public String Image;
    public int NumChapter;
    public long CategoryId;
    public List<String> Tags;
    public int Status;
    
}
