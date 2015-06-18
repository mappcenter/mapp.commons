/*e.getMessage()
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseUtils;

import com.nct.framework.common.LogUtil;
import config.ConfigInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author tuehm
 */
public class DatabaseAccess {
//

    public static Logger logger = LogUtil.getLogger(DatabaseAccess.class);

    public static DatabaseAccess getInstance() {
        return new DatabaseAccess();
    }
}
