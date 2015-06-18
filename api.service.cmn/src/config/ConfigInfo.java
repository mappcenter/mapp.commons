/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.nct.framework.common.Config;
import com.nct.framework.util.ConvertUtils;

/**
 *
 * @author Pham Liem
 */
public class ConfigInfo {

    private static final String configServerName = "api_server";
    public static final String APPLICATION_JSON = "application/json";
    
    public static int MIN_THREAD = ConvertUtils.toInt(Config.getParam(configServerName, "minthread"));
    public static int MAX_THREAD = ConvertUtils.toInt(Config.getParam(configServerName, "maxthread"));
    public static String PORT_LISTEN = Config.getParam(configServerName, "port");
    public static String HOST_LISTEN = Config.getParam(configServerName, "host");
    public static String ROOT_URL = Config.getParam(configServerName, "url"); 
    
    //<editor-fold defaultstate="collapsed" desc="Service Server">
    public static final String NCT_NOTIFICATION_DB = "nct_notification_db";
    //</editor-fold>
    
  
    
}
