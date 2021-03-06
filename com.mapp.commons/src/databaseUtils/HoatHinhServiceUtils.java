/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseUtils;

import com.nct.framework.common.LogUtil;
import com.nct.framework.dbconn.ClientManager;
import com.nct.framework.dbconn.ManagerIF;
import com.nct.framework.util.ConvertUtils;
import crawlers.hoathinhhd.entities.MovieEnt;
import crawlers.hoathinhhd.entities.QualityEnt;
import crawlers.hoathinhhd.entities.VideoEnt;
import entities.CategoryEnt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author MrFlex
 */
public class HoatHinhServiceUtils {
    private static final Logger logger = LogUtil.getLogger(HoatHinhServiceUtils.class);
    private static final String HOATHINH_DB_CONFIG_NAME = "hoathinh_server_db";
    
    public static long CreateMovie(MovieEnt movieEnt) {
        long idReturn = 0L;
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
        try {
            String queryCheck = "SELECT `Id` FROM `Movies` WHERE `Source`=?;";
            PreparedStatement stmtCheck = cnn.prepareStatement(queryCheck);
            stmtCheck.setString(1, movieEnt.Source);
            ResultSet resultSet = stmtCheck.executeQuery();
            if(resultSet.next()){
                long tmpId = ConvertUtils.toLong(resultSet.getString("Id"));
                if(tmpId>0){
                    return tmpId;
                }
            }
            resultSet.close();
            stmtCheck.close();
            
            String query = "INSERT INTO `Movies` (`Name`, `Image`, `Cover`, `Categories`, `Description`, `Country`, `Time`, `Tags`, `ArtistIds`, `Status`, `Source`)"
                    + " VALUE (?,?,?,?,?,?,?,?,?,?,?);";
            
            PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movieEnt.Name);
            stmt.setString(2, movieEnt.Image);
            stmt.setString(3, movieEnt.Cover);
            stmt.setString(4, StringUtils.join(movieEnt.Categories, ","));
            stmt.setString(5, movieEnt.Description);
            stmt.setString(6, movieEnt.Country);
            stmt.setString(7, movieEnt.Time);
            stmt.setString(8, StringUtils.join(movieEnt.Tags, ","));
            stmt.setString(9, StringUtils.join(movieEnt.ArtistIds, ","));
            stmt.setInt(10, movieEnt.Status);
            stmt.setString(11, movieEnt.Source);
            
            int result = stmt.executeUpdate();
            if(result>0){
                ResultSet tableKeys = stmt.getGeneratedKeys();
                if(tableKeys.next()){
                    long autoGeneratedID = tableKeys.getLong(1);
                    idReturn = (autoGeneratedID>0) ? autoGeneratedID : 0;
                }
            }
        } catch (SQLException ex){
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        
        return idReturn;
    }
    
    public static List<MovieEnt> GetListMovie(int movieStatus) {
        List<MovieEnt> listReturn = new ArrayList<MovieEnt>();
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
     
        try {
            String query = "SELECT * FROM `Movies` WHERE `Status` = ? ;";
            PreparedStatement stmt = cnn.prepareStatement(query);
            stmt.setInt(1, movieStatus);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                MovieEnt tmpCineChannelEnt = new MovieEnt();
                tmpCineChannelEnt.Id = ConvertUtils.toLong(resultSet.getString("Id"));
                tmpCineChannelEnt.Name = ConvertUtils.toString(resultSet.getString("Name"));
                tmpCineChannelEnt.Image = ConvertUtils.toString(resultSet.getString("Image"));
                tmpCineChannelEnt.Cover = ConvertUtils.toString(resultSet.getString("Cover"));
                tmpCineChannelEnt.Description = ConvertUtils.toString(resultSet.getString("Description"));
                tmpCineChannelEnt.Country = ConvertUtils.toString(resultSet.getString("Country"));
                tmpCineChannelEnt.Time = ConvertUtils.toString(resultSet.getString("Time"));
                tmpCineChannelEnt.Categories = new ArrayList<Long>();
                tmpCineChannelEnt.Tags = new ArrayList<String>();
                tmpCineChannelEnt.ArtistIds = new ArrayList<Long>();
                tmpCineChannelEnt.Status = ConvertUtils.toInt(resultSet.getString("Status"));
                tmpCineChannelEnt.Source = ConvertUtils.toString(resultSet.getString("Source"));
                
                listReturn.add(tmpCineChannelEnt);
            }
        }catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return listReturn;
    }
    
    public static List<VideoEnt> GetListVideo(int videoStatus) {
        List<VideoEnt> listReturn = new ArrayList<VideoEnt>();
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
     
        try {
            String query = "SELECT * FROM `Videos` WHERE `Status` = ? ;";
            PreparedStatement stmt = cnn.prepareStatement(query);
            stmt.setInt(1, videoStatus);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                VideoEnt tmpVideoEnt = new VideoEnt();
                tmpVideoEnt.Id = ConvertUtils.toLong(resultSet.getString("Id"));
                tmpVideoEnt.Name = ConvertUtils.toString(resultSet.getString("Name"));
                tmpVideoEnt.Image = ConvertUtils.toString(resultSet.getString("Image"));
                tmpVideoEnt.MovieId = ConvertUtils.toLong(resultSet.getString("MovieId"));
                tmpVideoEnt.Categories = new ArrayList<Long>();
                tmpVideoEnt.Status = ConvertUtils.toInt(resultSet.getString("Status"));
                tmpVideoEnt.Source = ConvertUtils.toString(resultSet.getString("Source"));
                
                listReturn.add(tmpVideoEnt);
            }
        }catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return listReturn;
    }
    
    public static long CreateCategory(CategoryEnt categoryEnt) {
        long categoryReturn = 0L;
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
        try {
            String query = "INSERT INTO `Category` (`Name`, `Parent`, `Tree`, `Module`) VALUE(?,?,?,?) ;";
            PreparedStatement stmt = cnn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, categoryEnt.Name);
            stmt.setLong(2, categoryEnt.Parent);
            stmt.setString(3, categoryEnt.Tree);
            stmt.setString(4, categoryEnt.Module);

            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next())
                    categoryReturn = generatedKeys.getLong(1);
            }
        }catch (SQLException ex){
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return categoryReturn;
    }
    
    public static long CheckExistNameWithParent(long parentId, String categoryName) {
        long categoryReturn = 0L;
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
     
        try {
            String query = "SELECT * FROM `Category` WHERE `Name` = ? ;";
            PreparedStatement stmt = cnn.prepareStatement(query);
//            stmt.setLong(1, parentId);
            stmt.setString(1, categoryName);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                long tmpId = ConvertUtils.toLong(resultSet.getString("Id"));
                if (tmpId > 0L)
                    categoryReturn = tmpId;
            }
        }catch (SQLException ex) {
            logger.error(LogUtil.stackTrace(ex));
        } finally {
            cm.returnClient(cnn);
        }
        return categoryReturn;
    }
    
    public static long CreateListStream(List<QualityEnt> listItems) {
        long result = 0;
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
        try {
            String query = "INSERT INTO `Streams` (Link, Type, Quality, VideoId, Status, Source) VALUES %s ;";
            String valuesQuery = "";
            NotifyPrepareStatement stmtValues = new NotifyPrepareStatement("(?,?,?,?,?,?)");
            for (QualityEnt tmpQualityEnt : listItems) {
                stmtValues.setString(1, tmpQualityEnt.Link);
                stmtValues.setString(2, tmpQualityEnt.Type);
                stmtValues.setString(3, tmpQualityEnt.Quality);
                stmtValues.setLong(4, tmpQualityEnt.VideoId);
                stmtValues.setInt(5, tmpQualityEnt.Status);
                stmtValues.setString(6, tmpQualityEnt.Source);
                valuesQuery += stmtValues.toString() + ",";
            }
            while (valuesQuery.endsWith(",")) {
                valuesQuery = valuesQuery.substring(0, valuesQuery.lastIndexOf(","));
            }
            PreparedStatement stmt = cnn.prepareStatement(String.format(query, valuesQuery), Statement.RETURN_GENERATED_KEYS);
            int resultVal = stmt.executeUpdate();
            if(resultVal>0){
                ResultSet tableKeys = stmt.getGeneratedKeys();
                if(tableKeys.next()){
                    long autoGeneratedID = tableKeys.getLong(1);
                    if(autoGeneratedID>0){
                        return autoGeneratedID;
                    }
                }
            }
            
            stmt.close();
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            return 0;
        } finally {
            cm.returnClient(cnn);
        }
        return result;
    }
    
    public static long CreateListVideo(List<VideoEnt> listItems) {
        long result = 0;
        ManagerIF cm = ClientManager.getInstance(HOATHINH_DB_CONFIG_NAME);
        Connection cnn = cm.borrowClient();
        try {
            String query = "INSERT INTO `Videos` (Name, Image, MovieId, Categories, Status, Source) VALUES %s ;";
            String valuesQuery = "";
            NotifyPrepareStatement stmtValues = new NotifyPrepareStatement("(?,?,?,?,?,?)");
            for (VideoEnt tmpVideoEnt : listItems) {
                stmtValues.setString(1, tmpVideoEnt.Name);
                stmtValues.setString(2, tmpVideoEnt.Image);
                stmtValues.setLong(3, tmpVideoEnt.MovieId);
                String category =  (tmpVideoEnt.Categories!=null) ? StringUtils.join(tmpVideoEnt.Categories, ",") : "";
                stmtValues.setString(4, category);
                stmtValues.setInt(5, tmpVideoEnt.Status);
                stmtValues.setString(6, tmpVideoEnt.Source);
                valuesQuery += stmtValues.toString() + ",";
            }
            while (valuesQuery.endsWith(",")) {
                valuesQuery = valuesQuery.substring(0, valuesQuery.lastIndexOf(","));
            }
            PreparedStatement stmt = cnn.prepareStatement(String.format(query, valuesQuery), Statement.RETURN_GENERATED_KEYS);
            int resultVal = stmt.executeUpdate();
            if(resultVal>0){
                ResultSet tableKeys = stmt.getGeneratedKeys();
                if(tableKeys.next()){
                    long autoGeneratedID = tableKeys.getLong(1);
                    if(autoGeneratedID>0){
                        return autoGeneratedID;
                    }
                }
            }
            
            stmt.close();
        } catch (Exception ex) {
            logger.error(LogUtil.stackTrace(ex));
            return 0;
        } finally {
            cm.returnClient(cnn);
        }
        return result;
    }
}
