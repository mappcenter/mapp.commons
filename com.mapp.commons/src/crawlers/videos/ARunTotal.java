/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawlers.videos;

import com.nct.framework.common.Config;
import com.nct.framework.common.LogUtil;
import com.nct.framework.util.ConvertUtils;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author MrFlex
 */
public class ARunTotal {
    private static final String configName = "main_settings";
    private static final String SAVE_FOLDER = ConvertUtils.toString(Config.getParam(configName, "folder"), "");
    private static final Logger logger = LogUtil.getLogger(ARunTotal.class);
    private static final String filePath = SAVE_FOLDER + "ARunTotal.txt";
    
    public static void main(String[] args) {
        try {
            String dateFiles = "";
            
            //Get Video
            dateFiles += GioiTreViet.getVideoMain();
            
            
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
