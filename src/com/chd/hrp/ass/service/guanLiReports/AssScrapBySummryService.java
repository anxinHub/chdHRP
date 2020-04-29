package com.chd.hrp.ass.service.guanLiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 报废汇总 查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssScrapBySummryService {
	
	/**
	 * 报废汇总  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String query(Map<String, Object> page)  throws DataAccessException ;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssScrapBySummryPrint(Map<String, Object> entityMap) throws DataAccessException;


}
