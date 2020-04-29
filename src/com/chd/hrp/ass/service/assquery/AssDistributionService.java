package com.chd.hrp.ass.service.assquery;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 全院固定资产分布
 * @author fhqfm
 *
 */
public interface AssDistributionService {

	/**
	 * 查询全院固定资产分布
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssDistribution(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssDistributionPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssbuttonPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssbuttonsPrint(Map<String, Object> entityMap) throws DataAccessException;

}
