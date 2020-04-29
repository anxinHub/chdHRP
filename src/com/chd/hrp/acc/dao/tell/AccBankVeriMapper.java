package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBankVeri;

public interface AccBankVeriMapper extends SqlMapper{
	/**
	 * @Description 
	 * 坏账提取表<BR> 添加AccBankVeri
	 * @param AccBankVeri entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBankVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量添加AccBankVeri
	 * @param  AccBankVeri entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccBankVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addAccBankVeriLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeri分页
	 * @param  entityMap RowBounds
	 * @return List<AccBankVeri>
	 * @throws DataAccessException
	*/
	public List<AccBankVeri> queryAccBankVeri(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 查询AccBankVeri所有数据
	 * @param  entityMap
	 * @return List<AccBankVeri>
	 * @throws DataAccessException
	*/
	public List<AccBankVeri> queryAccBankVeri(Map<String,Object> entityMap) throws DataAccessException;
	
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
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBankVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 删除deleteAccBankVeriBySubjAndDate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBankVeriBySubjAndDate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 坏账提取表<BR> 批量删除AccBankVeri
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccBankVeri(List<Map<String, Object>> entityMap)throws DataAccessException;

}
