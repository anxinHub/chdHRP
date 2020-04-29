/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.charge;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

 

public interface BudgChargeAccountBegainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 期初记账日期下拉框  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String,Object>> queryBudgMarkDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public int addDetail(Map<String, Object> entityMap) throws DataAccessException;

	public void addLastTotalYearData(Map<String, Object> entityMap) throws DataAccessException;

	public void addLastTotalYearDataDetail(Map<String, Object> entityMap) throws DataAccessException;
/*
	查询数据主表中的所有数据
*/	
	public List<Map<String, Object>> queryBudgProjBegain(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryBudgProjBegainDetail(Map<String, Object> entityMap) throws DataAccessException;

	/*public Map<String, Object> queryMainYear(Map<String, Object> entityMap)  throws DataAccessException;

	public Map<String, Object> queryDetailYear(Map<String, Object> entityMap) throws DataAccessException;*/
	
}
