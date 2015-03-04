/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.common.LogUtil;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author liempt
 */
public class XemGame_Com {
    private static final Logger logger = LogUtil.getLogger(XemGame_Com.class);
    private static final String filePath = "/home/liempt/Desktop/XemGame_Com.txt";
    
    public static void main(String[] args) {        
        String urlX = "http://www.xemgame.com/video";
            
        try {
            String dateFiles = "";
            for(int i=1;i<300;i++){
                String tmpX = String.format(urlX, i);
//                List<VideoLinkEnt> tmpVideoLinkEnt = getVideoLinkEnt(tmpX);
//                if(tmpVideoLinkEnt!=null){
//                    for(VideoLinkEnt xVideo : tmpVideoLinkEnt){
//                        dateFiles += xVideo.Link+"--"+xVideo.Title+"\n";
//                    }
//                    files.FileUtils.WriteDatFile("/home/liempt/Desktop/gioitreviet.txt", dateFiles);
//                    System.out.println("Id:"+i+" => "+JSONUtil.Serialize(tmpVideoLinkEnt));
//                }
            }
            //Write to Files
            File file = new File(filePath); 
            if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
            }else{
                    System.out.println("Delete operation is failed.");
            }
            files.FileUtils.WriteDatFile(filePath, dateFiles);
        
        } catch (Exception e) {
            logger.error(LogUtil.stackTrace(e));
        }
        
    }
}
