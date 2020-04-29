/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccDeptWageItemService {

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccDeptWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccDeptWageSum(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccDeptWageSumPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccDeptWageItemPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public abstract String queryAccDeptWageItemSum(Map<String, Object> paramMap)
			    throws DataAccessException;
}
