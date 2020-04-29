package com.chd.hrp.budg.service.budgpur.carry;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 材料采购预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgMatPurCarryService {
	
	/**
	 *结转
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String chargeBudgMatPurCarry(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 反结
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String reChargeBudgMatPurCarry(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * 查询 已结转年月、待结转年月
	 * @param entityMap
	 * @return String
	 */
	public String queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;
}
