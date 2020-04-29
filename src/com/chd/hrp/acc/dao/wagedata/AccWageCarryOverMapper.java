/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccYearMonth;

/**
* @Title. @Description.
* 结转工资<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageCarryOverMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 结转工资<BR> 更新AccYearMonth
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccYearMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	//取当前年月格式(2016年06月)
	public String queryAccYearMonthNow(Map<String,Object> entityMap) throws DataAccessException;
	
	//取上次年月格式(2016年06月)
	public String queryAccYearMonthLast(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccYearMonth> queryNextAccYearMonth(Map<String,Object> entityMap) throws DataAccessException;
		
}
