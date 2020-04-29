package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccTell;

public interface AccBankByAccountService {

	/**
	 * @Description 
	 * 银行出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBankByAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBankByAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 批量删除AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBankByAccount(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 修改AccTell
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateAccBankByAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 查询AccTell
	 * @param entityMap
	 * @return AccTell
	 * @throws DataAccessException
	*/
	public AccTell queryAccBankByAccountByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
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
	
	public String addBatchAccBankAccount(String entityList) throws DataAccessException;
	
}

