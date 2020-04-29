package com.chd.hrp.budg.dao.business.purchase;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 资产采购预算执行
 * @author Administrator
 *
 */
public interface ExecuteMapper extends SqlMapper{

	public List<Map<String, Object>> querySourceName(Map<String, Object> mapVo)throws DataAccessException;

	public int updateBudgCashFlowBegin(List<Map<String, Object>> updateList)throws DataAccessException;

	public List<Map<String, Object>> queryBudgCashFlowBegin(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 批量校验数据是否存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryExecuteDataExist(List<Map<String, Object>> addList)throws DataAccessException;
	
	/**
	 * 单条校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

}
