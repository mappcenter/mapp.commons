/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceUtils;

import com.nct.framework.common.LogUtil;
import com.nct.framework.dbconn.ClientManager;
import com.nct.framework.dbconn.ManagerIF;
import com.nct.framework.util.ConvertUtils;
import entities.HoatHinhHD.CategoryEnt;
import entities.HoatHinhHD.MovieEnt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author MrFlex
 */
public class HoatHinhServiceUtils {
    private static final Logger logger = LogUtil.getLogger(HoatHinhServiceUtils.class);
    private static final String HOATHINH_DB_CONFIG_NAME = "hoathinh_server_db";
    
    
    
    public static List<MovieEnt> GetListMovie(long categoryId, int pageIndex, int pageSize) {
        List<MovieEnt> listReturn = new ArrayList<MovieEnt>();
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
     
        try {
            String query = "SELECT * FROM `Movies` WHERE (`Categories` LIKE '?,%' OR `Categories` LIKE '%,?,%' OR `Categories` LIKE '%,?') AND `Status` = ? LIMIT ?,?;";
            PreparedStatement stmt = cnn.prepareStatement(query);
            stmt.setLong(1, categoryId);
            stmt.setLong(2, categoryId);
            stmt.setLong(3, categoryId);
            stmt.setInt(4, 1);
            stmt.setInt(5, (pageIndex-1)*pageSize);
            stmt.setInt(6, pageSize);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MovieEnt tmpMovieEnt = new MovieEnt();
                tmpMovieEnt.Id = ConvertUtils.toString(resultSet.getString("Id"));
                tmpMovieEnt.Name = ConvertUtils.toString(resultSet.getString("Name"));
                tmpMovieEnt.Image = ConvertUtils.toString(resultSet.getString("Image"));
                tmpMovieEnt.Cover = ConvertUtils.toString(resultSet.getString("Cover"));
                tmpMovieEnt.Description = ConvertUtils.toString(resultSet.getString("Description"));
                tmpMovieEnt.Country = ConvertUtils.toString(resultSet.getString("Country"));
                tmpMovieEnt.Count = ConvertUtils.toString(resultSet.getString("Time"));
                
                listReturn.add(tmpMovieEnt);
            }
        }catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return listReturn;
    }
    
    public static List<CategoryEnt> GetListCategory() {
        List<CategoryEnt> listReturn = new ArrayList<CategoryEnt>();
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
     
        try {
            String query = "SELECT * FROM `Category` WHERE `Module` = ? ;";
            PreparedStatement stmt = cnn.prepareStatement(query);
            stmt.setString(1, "HoatHinhHD");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                CategoryEnt tmpCategoryEnt = new CategoryEnt();
                tmpCategoryEnt.Id = ConvertUtils.toString(resultSet.getString("Id"));
                tmpCategoryEnt.Name = ConvertUtils.toString(resultSet.getString("Name"));
                tmpCategoryEnt.Icon = "";
                
                listReturn.add(tmpCategoryEnt);
            }
        }catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return listReturn;
    }
}
