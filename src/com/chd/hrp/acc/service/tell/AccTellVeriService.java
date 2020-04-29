package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccTellVeri;

public interface AccTellVeriService {
	/**
	 * @Description 
	 * 添加AccBankVeri
	 * @param AccBankVeri entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAccTellVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加AccBankVeri
	 * @param  AccBankVeri entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAccTellVeri(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询AccBankVeri所有数据
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccTellVeri(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询AccBankVeriByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccTellVeri queryAccTellVeriByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除AccBankVeri
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAccTellVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除deleteAccBankVeriBySubjAndDate
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAccTellVeriBySubjAndDate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除AccBankVeri
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccTellVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryAccVouchCheck(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 出纳对账  出纳数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccTellDatas(Map<String, Object> entityMap) throws DataAccessException;

}
