package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 库存报表<BR>
*/
public interface AccInventoryReportService {
	
	/**
	 * @Description 
	 * 库存日报<BR> 查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccInventoryReportByDay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存日报<BR> 打印
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccInventoryReportByDayPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存月报<BR> 查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccInventoryReportByMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存月报<BR> 打印
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccInventoryReportByMonthPrint(Map<String,Object> entityMap)throws DataAccessException;
	
}
