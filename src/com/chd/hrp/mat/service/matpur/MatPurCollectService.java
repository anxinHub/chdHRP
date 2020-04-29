package com.chd.hrp.mat.service.matpur;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * @Description:
 * 采购汇总查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatPurCollectService  {
	 
	
	/**
	 * 材料采购查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	public String queryMatInvPurMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 材料采购查询-打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatInvPurMainPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurCollect(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 库存明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatPurCollectPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurCollectType(Map<String, Object> entityMap) throws DataAccessException;
	public Map<String, Object> ViewSqlMatType(Map<String, Object> entityMap) throws DataAccessException;
	
	
}


