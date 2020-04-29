package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchOut;

public interface AccMatchOutService {
	/**
	 * @Description 
	 * 配套资金支出表<BR> 添加AccMatchOut
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量添加AccMatchOut
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 查询AccMatchOut分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryAccMatchOut(Map<String,Object> entityMap) throws DataAccessException;
	
	//配套资金支出   打印
	public List<Map<String, Object>> queryAccMatchOutPrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 查询AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchOut queryAccMatchOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金支出表<BR> 更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
}
