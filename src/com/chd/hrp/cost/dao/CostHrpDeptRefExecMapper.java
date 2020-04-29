/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostHrpDeptRefExecMapper extends SqlMapper{
	
	
	/**
	 * 导入时 校验  系统科室编码是否合法
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryHrpDeptDate(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 导入时 校验  自定义科室编码是否合法
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryCostDeptDate(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 系统科室下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrpDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 自定义科室下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostCustomDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 停用
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int  endCostHrpDeptExecRef(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
}
