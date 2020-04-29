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
* 结转工资<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageCarryOverService {

	/**
	 * @Description 
	 * 结转工资<BR> 更新AccYearMonth
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccYearMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结转工资<BR> 查询当前年月
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccYearMonthNow(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结转工资<BR> 查询当前年月
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccYearMonthLast(Map<String, Object> entityMap)throws DataAccessException;
}
