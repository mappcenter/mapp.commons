/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceUtils;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.GetCallback;

/**
 *
 * @author MrFlex
 */
public class ParseUtils {
    private static final String PARSE_APP_ID = "Aingn94q6Ar0Rx7vjS4nB3APe0hoi3sLeMguwPjq";
    private static final String PARSE_REST_API_KEY = "Fv9YVkSGrjFhMbzZX71Fkrc5emKGez2YJR4jhEXb";
    
    public static boolean setCacheParse(String cacheKey, String cacheValue) {
        try {
            Parse.initialize(PARSE_APP_ID, PARSE_REST_API_KEY);        

            ParseObject gameScore = new ParseObject("DataCached");
            gameScore.put("sKey", cacheKey);
            gameScore.put("sValue", cacheValue);
            gameScore.save();
        }catch (Exception ex){
            return false;
        }
        return false;
    }
    
    public static String getCacheParse(String cacheKey) {
        final String result = "";
        try {
            Parse.initialize(PARSE_APP_ID, PARSE_REST_API_KEY);
            
            ParseQuery<ParseObject> query = ParseQuery.getQuery("DataCached");
            query.getInBackground(cacheKey, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String title = object.getString("Value");
                        System.out.println(title);
                    } else {
                        
                    }
                }
            }); 
        }catch (Exception ex){
            return "";
        }
        return result;
    }
}
