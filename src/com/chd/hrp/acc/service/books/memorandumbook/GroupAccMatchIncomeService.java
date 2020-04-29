package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchIncome;

public interface GroupAccMatchIncomeService {
	/**
	 * @Description 
	 * 配套资金收入表<BR> 添加AccMatchIncome
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量添加AccMatchIncome
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryGroupAccMatchIncome(Map<String,Object> entityMap) throws DataAccessException;
	//配套资金收入  打印
	public List<Map<String, Object>> queryGroupAccMatchIncomePrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 查询AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchIncome queryGroupAccMatchIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量删除AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金收入表<BR> 更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateGroupAccMatchIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金收入表<BR> 批量更新AccMatchIncome
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
}
