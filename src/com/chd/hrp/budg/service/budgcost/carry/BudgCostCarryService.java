package com.chd.hrp.budg.service.budgcost.carry;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 医疗支出预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCostCarryService {
	
	/**
	 *结转
	 * @param mapVo
	 * @return String
	 * @throws DataAccessException
	 */
	public String chargeBudgCostCarry(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 反结
	 * @param mapVo
	 * @return String
	 * @throws DataAccessException
	 */
	public String reChargeBudgCostCarry(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * 查询 已结转年月、待结转年月
	 * @param mapVo
	 * @return String
	 */
	public String queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;


}
