/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceUtils;

import commonUtils.CommonUtils;
import databaseUtils.CineServiceUtils;
import entities.CategoryEnt;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MrFlex
 */
public class CategoryServiceUtils {
    
    
    public static List<Long> getListCategoryIds(List<String> categories, String catModule, long catParentId, String catParentName) {
        List result = new ArrayList();
        if ((categories == null) || (categories.isEmpty())) {
            return result;
        }
        for (String tmpCategory : categories) {
          if (!CommonUtils.IsNullOrEmpty(tmpCategory)) {
            long tmpCategoryId = CineServiceUtils.CheckExistNameWithParent(catParentId, tmpCategory);
            if (tmpCategoryId > 0) {
                result.add(tmpCategoryId);
            } else {
                CategoryEnt tmpCategoryEnt = new CategoryEnt();
                tmpCategoryEnt.Module = catModule;
                tmpCategoryEnt.Name = tmpCategory;
                tmpCategoryEnt.Parent = catParentId;
                tmpCategoryEnt.Tree = catParentName+">"+tmpCategory;
                tmpCategoryId = CineServiceUtils.CreateCategory(tmpCategoryEnt);
                if (tmpCategoryId > 0) {
                  result.add(tmpCategoryId);
                }
            }
          }
        }
     return result;
    }
}
