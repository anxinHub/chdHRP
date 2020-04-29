package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccInventoryReport;

/**
 * @Description 
 * 库存报表<BR> 
*/
public interface AccInventoryReportMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 库存日报<BR> 查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<AccInventoryReport> queryAccInventoryReportByDay(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccInventoryReportByDayPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存日报<BR> 查询 分页
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public  List<AccInventoryReport> queryAccInventoryReportByDay(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 库存月报<BR> 查询
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<AccInventoryReport> queryAccInventoryReportByMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccInventoryReportByMonthPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库存月报<BR> 查询 分页
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public  List<AccInventoryReport> queryAccInventoryReportByMonth(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
}
