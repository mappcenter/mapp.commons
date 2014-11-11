/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package commonUtils;

import com.nct.framework.util.ConvertUtils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LiemPT
 */
public class CommonUtils {
    public static boolean IsNullOrEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static List<String> ParseStringToListString(String str) {
        List<String> result = new ArrayList<String>();
        if (IsNullOrEmpty(str)) {
            return result;
        }
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; ++i) {
            if (!IsNullOrEmpty(arr[i])) {
                result.add(ConvertUtils.toString(arr[i].trim()));
            }
        }
        return result;
    }

    public static boolean IsValidEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();

    }

    public static boolean IsExistsLink(String inputLink) {
        try{
            URL url = new URL(inputLink);
            HttpURLConnection huc = (HttpURLConnection)  url.openConnection(); 
            huc.setRequestMethod("GET"); 
            huc.connect(); 
            System.out.println(huc.getResponseCode());
            if(huc.getResponseCode()==200){
                return true;
            }
            
        }catch(Exception ex){
            return false;
        }
        return false;
    }
}
