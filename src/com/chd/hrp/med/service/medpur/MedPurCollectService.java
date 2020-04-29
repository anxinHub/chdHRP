package com.chd.hrp.med.service.medpur;

import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * @Description:
 * 采购汇总查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedPurCollectService  {
	
	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurCollect(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 库存明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurCollectType(Map<String, Object> entityMap) throws DataAccessException;
	public Map<String, Object> ViewSqlMedType(Map<String, Object> entityMap) throws DataAccessException;
	
	
}


