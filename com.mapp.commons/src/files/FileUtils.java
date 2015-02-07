/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package files;

import commonUtils.CommonUtils;
import static commonUtils.CommonUtils.IsNullOrEmpty;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author LiemPT
 */
public class FileUtils {
    public static String ReadTextFile(String pathFileText, String charEnter) {
        String dataResult = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathFileText));        
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                sb.append(line).append(charEnter);
                line = reader.readLine();
            }
            dataResult = sb.toString(); 
            reader.close();
        }catch (IOException ex){
            
        }
        return dataResult;
    }
    
    public static List<String> ReadTextFileToList(String pathFileText) {
        List<String> dataResult = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathFileText));        
            String line = reader.readLine();

            while (line != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(line);
                dataResult.add(sb.toString().trim());
                line = reader.readLine();
            }
            reader.close();
        }catch (IOException ex){
            
        }
        return dataResult;
    }
    
    public static boolean WriteFile(String pathFileText, String fileContent) {
        try {
            File file = new File(pathFileText);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //Collecting Data
            if(!CommonUtils.IsNullOrEmpty(fileContent)){
                //true = append file
                FileWriter fileWritter = new FileWriter(file.getPath(), false);
                try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                    bufferWritter.write(fileContent);
                }                
                return true;
            }
        }catch (IOException ex){
            return false;
        }
        return false;
    }
    
    public static boolean WriteDatFile(String pathFileText, String fileContent) {
        try {
            File file = new File(pathFileText);

            //if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //Collecting Data
            if(!IsNullOrEmpty(fileContent)){
                //true = append file
                FileWriter fileWritter = new FileWriter(file.getPath(), true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(fileContent);
                bufferWritter.close();
                
                return true;
            }
        }catch (Exception ex){
            return false;
        }
        return false;
    }
}
