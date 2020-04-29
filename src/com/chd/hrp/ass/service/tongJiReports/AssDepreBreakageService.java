package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 050701 资产报损查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDepreBreakageService {
	
	/**
	 * 资产报损查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String query(Map<String, Object> page)  throws DataAccessException ;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssDepreBreakagePrint(Map<String, Object> entityMap) throws DataAccessException;


}
