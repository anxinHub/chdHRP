/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.base;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrTitleLevel;
/**
 * 
 * @Description:
 * 职称等级
 * @Table:
 * HR_TITLE_LEVEL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTitleLevelMapper extends SqlMapper{
   /**
    * 删除职称等级
    * @param entityList
    */
	void deleteTitleLevel(List<HrTitleLevel> entityList);
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
    List<HrTitleLevel> queryTitleLevelById(Map<String, Object> entityMap);
	List<HrTitleLevel> queryTitleLevelByIdName(Map<String, Object> entityMap);
	
}
