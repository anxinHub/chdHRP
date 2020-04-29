/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.dao.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description: 8804 科室字典变更表
 * @Table: APHI_DEPT_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmDimReportMapper extends SqlMapper {

	/**
	 * @Description 查询结果集BottomGrid<BR>
	 *              全部
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrmDimReportBottomGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询结果集BottomRightGrid<BR>
	 *              全部
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrmDimReportRightGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询结果集BottomRightGrid<BR>
	 *              全部
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrmECharReportBottomGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	

}
