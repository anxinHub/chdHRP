/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccDeptWageItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccPeopleWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccDeptWageItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem所有数据
	 * @param  entityMap
	 * @return List<AccPeopleWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccDeptWageItem(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryAccDeptWageSum(Map<String,Object> entityMap) throws DataAccessException;

	public abstract List<Map<String, Object>> queryAccDeptWageItemSum(Map<String, Object> paramMap)
			    throws DataAccessException;

	public abstract List<Map<String, Object>> queryAccDeptWageItemSum(Map<String, Object> paramMap, RowBounds rowBounds)
			    throws DataAccessException;
}
