/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlet.controller;

import com.nct.framework.common.LogUtil;
import com.nct.framework.util.ConvertUtils;
import commonUtils.ResultCode;
import config.ConfigInfo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author liempt
 */
public class MainController extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(MainController.class);    

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(ConfigInfo.APPLICATION_JSON+";charset=UTF-8");
        PrintWriter out = response.getWriter();
        String jsonTemplateResult = "{\"ResultCode\":\"%s\",\"Message\":\"%s\",\"IsMore\":\"%s\",\"Data\":\"%s\"}";
        String outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_WITH_MESSAGE, "ERROR.SYSTEM_ERROR", false, "");
        try {
            
                    
                    
            
        } catch (Exception ex) {
            String urlBack = ConvertUtils.toString(request.getRequestURI());
            String queryString = request.getQueryString();
            logger.warn("URL: " + urlBack + "?" + queryString);
            logger.error(LogUtil.stackTrace(ex));
            outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_WITH_MESSAGE, "ERROR.ACTION_FALSE", false, "");
        } finally {
            out.println(outputJson);
            out.close();
        
        }
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(ConfigInfo.APPLICATION_JSON+";charset=UTF-8");
        PrintWriter out = response.getWriter();
        String jsonTemplateResult = "{\"ResultCode\":\"%s\",\"Message\":\"%s\",\"IsMore\":\"%s\",\"Data\":\"%s\"}";
        String outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_WITH_MESSAGE, "ResultCode.ERROR.SYSTEM_ERROR", false, "");
        try {
            
            
        } catch (Exception ex) {
            String urlBack = ConvertUtils.toString(request.getRequestURI());
            String queryString = request.getQueryString();
            logger.warn("URL: " + urlBack + "?" + queryString);
            logger.error(LogUtil.stackTrace(ex));
            outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_WITH_MESSAGE, "ResultCode.ERROR.ACTION_FALSE", false, "");
        } finally {
            out.println(outputJson);
            out.close();
        
        }
    }
}
