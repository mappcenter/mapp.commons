/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package httpService;

import com.nct.framework.common.LogUtil;
import org.apache.log4j.Logger;
import org.parse4j.Parse;
import org.parse4j.ParseObject;
import serviceUtils.ParseUtils;


/**
 *
 * @author liempt
 */
public class LocalService {
    private static final Logger logger = LogUtil.getLogger(LocalService.class);
    
    public static void main(String[] args) {
        System.out.println("BEGIN:");
//        ParseUtils.setCacheParse("TMP.CACHED", "Pham Liem");
        
        String cachedOut = ParseUtils.getCacheParse("TMP.CACHED");
        System.out.println(cachedOut);
        System.exit(0);
    }
}
