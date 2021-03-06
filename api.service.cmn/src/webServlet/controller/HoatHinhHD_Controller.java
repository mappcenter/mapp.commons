/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServlet.controller;

import com.nct.framework.common.LogUtil;
import com.nct.framework.util.ConvertUtils;
import com.nct.framework.util.JSONUtil;
import commonUtils.ResultCode;
import config.ConfigInfo;
import entities.HoatHinhHD.AdvEnt;
import entities.HoatHinhHD.CategoryEnt;
import entities.HoatHinhHD.HomeEnt;
import entities.HoatHinhHD.RelateApp;
import entities.HoatHinhHD.UpdateVersionEnt;
import entities.HoatHinhHD.MovieEnt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import serviceUtils.HoatHinhServiceUtils;

/**
 *
 * @author MrFlex
 */
public class HoatHinhHD_Controller extends HttpServlet {
    private static final Logger logger = LogUtil.getLogger(HoatHinhHD_Controller.class);    

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
        String jsonTemplateResult = "{\"ErrorCode\":\"%s\",\"Message\":\"%s\",\"More\":\"%s\",\"Data\":\"%s\"}";
        String outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_WITH_MESSAGE, "ERROR.SYSTEM_ERROR", false, "");
        try {
            String action = request.getParameter("action");
            switch(action){
                case "version":
                    outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(new  UpdateVersionEnt()));
                    break;
                case "category":
                    List<CategoryEnt> listCategory = HoatHinhServiceUtils.GetListCategory();
                    if(listCategory!=null&&listCategory.size()>0){
                        outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(listCategory));
                    }else{
                        outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_NO_MESSAGE, "", false, JSONUtil.Serialize(""));
                    }
                    break;
                case "home":
                    List<MovieEnt> listHomeMovie = HoatHinhServiceUtils.GetListMovie(1, 1, 20);
                    HomeEnt homeEnt = new HomeEnt(null, listHomeMovie);
                    outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(homeEnt));
                    break;
                case "getlistcontent":
                    long catId = ConvertUtils.toLong(request.getParameter("id"));
                    int pageIndex = ConvertUtils.toInt(request.getParameter("page"));
                    List<MovieEnt> listMovie = HoatHinhServiceUtils.GetListMovie(catId, pageIndex, 20);
                    if(listMovie!=null&&listMovie.size()>0){
                        outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(listMovie));
                    }else{
                        outputJson = String.format(jsonTemplateResult, ResultCode.FAIL_NO_MESSAGE, "", false, JSONUtil.Serialize(""));
                    }
                    break;
                case "getitemdetail":
                    outputJson = "";
                    break;
                case "getsearch":
                    outputJson = "";
                    break;
                case "getlink":
                    outputJson = "";
                    break;
                case "getmoreapps":
                    RelateApp tmpRelateApp = new RelateApp("Cartoon Movie", " ★ > 2000 videos \n ★ Hot content and update daily \n ★ Dowload to view offline \n ★ View faster and unlimited download", "http://phimmobile.net/app/hdcartoon.png" ,"https://itunes.apple.com/us/app/cartoon-movie/id598847637?ls=1&mt=8");
                    outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(tmpRelateApp));
                    break;
                case "getadv":
                    AdvEnt tmpAdvEnt = new AdvEnt("http://hdtag.com/banner/btv-640x100.jpg", "http://bit.ly/IIqVvP");
                    outputJson = String.format(jsonTemplateResult, ResultCode.SUCCESS_NO_MESSAGE, "", false, JSONUtil.Serialize(tmpAdvEnt));
                    break;
                default:
                    break;
            }
            
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

