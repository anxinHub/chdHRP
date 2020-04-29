package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBankVeri;

public interface AccBankVeriService {

	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量添加AccBankVeri
	 * @param  AccBankVeri entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAccBankVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeri所有数据
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccBankVeri(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeriByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccBankVeri queryAccBankVeriByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除AccBankVeri
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAccBankVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除deleteAccBankVeriBySubjAndDate
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAccBankVeriBySubjAndDate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBankVeri
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBankVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
