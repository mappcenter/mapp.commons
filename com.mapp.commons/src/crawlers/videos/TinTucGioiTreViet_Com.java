/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.common.LogUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author liempt
 */
public class TinTucGioiTreViet_Com {
    private static final Logger logger = LogUtil.getLogger(GioiTreViet.class);
    private static final String filePath = "/home/liempt/Desktop/gioitreviet.txt";
    
    public static void main(String[] args) {        
        String urlX = "http://tintucgioitreviet.com/category/video/page/%s/";
            
        try {
        
        
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        
    }
}
