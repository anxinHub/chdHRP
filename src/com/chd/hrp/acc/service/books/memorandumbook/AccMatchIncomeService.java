package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchIncome;

public interface AccMatchIncomeService {
	/**
	 * @Description 
	 * 配套资金收入表<BR> 添加AccMatchIncome
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量添加AccMatchIncome
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryAccMatchIncome(Map<String,Object> entityMap) throws DataAccessException;
	//配套资金收入  打印
	public List<Map<String, Object>> queryAccMatchIncomePrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchIncome queryAccMatchIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金收入表<BR> 更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
}
