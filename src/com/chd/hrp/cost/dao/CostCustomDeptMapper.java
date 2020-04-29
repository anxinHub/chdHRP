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


public interface CostCustomDeptMapper extends SqlMapper{
	
	
	/**
	 * @Description 更新DeptCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 添加、修改部门时 查询  与输入 部门编码 或 部门名称 一样的部门记录 （ 判断部门编码、部门名称是否存在）
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object> > queryDeptById(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryDeptByCodeName(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 导入时 校验  科室类别编码是否合法
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptKindDate(Map<String, Object> map) throws DataAccessException;
}
