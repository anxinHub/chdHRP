package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccCashAccountService {
	/**
	 * @Description 
	 * 现金出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 批量删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashAccount(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 修改AccTell
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String updateAccCashAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 会计科目<BR> 更新流水号
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccTellMaxTellNumber(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 批量更新
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String updateBatchAccTell(List<Map<String,Object>> list) throws DataAccessException;
	
	public String addAccTellVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addBatchAccCashAccount(String entityList) throws DataAccessException;
	
	public String queryAccTellVouchState(String paramVo) throws DataAccessException;

	public String addBatchAccCash(List<Map<String, Object>> entityList) throws DataAccessException;

	public Long queryAccTellNextSeq() throws DataAccessException;
	
}
