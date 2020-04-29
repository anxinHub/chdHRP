package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrDeptViewMapper  extends SqlMapper{
    /**
     * 查询部门架构设置
     * @param map
     * @return
     */
	List<Map<String, Object>> queryDeptViewOrgSet(Map<String, Object> map);
   /**
    * 查询科室主任照片 
    * @param entityMap
    * @return
    */
	String queryDeptViewOrgImg(Map<String, Object> entityMap); 
	/**
	 * 查询右键菜单
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>>  queryDeptViewOrgMenu(Map<String, Object> entityMap);

}
