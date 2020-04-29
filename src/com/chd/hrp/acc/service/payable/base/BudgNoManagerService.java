package com.chd.hrp.acc.service.payable.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.payable.BudgNoManager;

public interface BudgNoManagerService {
	/**
	 * @Description 更新最大单号<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public int updateBudgNoManagerMaxNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return BudgNoManager
	 * @throws DataAccessException
	 */
	public BudgNoManager queryBudgNoManagerByName(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return BudgNoManager
	 * @throws DataAccessException
	 */
	public String getBillNOSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return BudgNoManager
	 * @throws DataAccessException
	 */
	public String getBillNOSeqNo(String tableName) throws DataAccessException;

	/**
	 * @Description 更新最大单号<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public int updateBudgNoManagerMaxNo(String tableName) throws DataAccessException;
}
