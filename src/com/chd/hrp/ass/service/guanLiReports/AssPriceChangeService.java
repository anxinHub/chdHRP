package com.chd.hrp.ass.service.guanLiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 资产原值变动
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssPriceChangeService {
	
	/**
	 * 业务类型动态表头
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryBusTypes(Map<String, Object> entityMap)  throws DataAccessException ;
	
	/**
	 * 资产原值变动
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String query(Map<String, Object> page)  throws DataAccessException ;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssPriceChangMainPrint(Map<String, Object> entityMap) throws DataAccessException;

}
