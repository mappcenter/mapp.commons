/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceUtils;

import commonUtils.CommonUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author liempt
 */
public class FileCacheUtils {
    private static final String FOLER_CACHED = "/media/liempt/DATA/Working/DataLogs/";
    
    public static boolean setCacheFile(String cacheKey, String cacheValue) {
        try {
            File file = new File(FOLER_CACHED + cacheKey);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //Collecting Data
            if(!CommonUtils.IsNullOrEmpty(cacheValue)){                
                //true = append file
                FileWriter fileWritter = new FileWriter(file.getPath(), false);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(cacheValue);
                bufferWritter.close();
                
                return true;
            }
        }catch (Exception ex){
            return false;
        }
        return false;
    }
    
    public static String getMemcache(String cacheKey) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FOLER_CACHED + cacheKey));        
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            result = sb.toString(); 
            reader.close();
        }catch (IOException ex){
            return "";
        }
        return result;
    }
}
