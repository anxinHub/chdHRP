package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchOut;

public interface GroupAccMatchOutService {
	/**
	 * @Description 
	 * 配套资金支出表<BR> 添加AccMatchOut
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addGroupAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量添加AccMatchOut
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 查询AccMatchOut分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryGroupAccMatchOut(Map<String,Object> entityMap) throws DataAccessException;
	
	//配套资金支出   打印
	public List<Map<String, Object>> queryGroupAccMatchOutPrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 查询AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchOut queryGroupAccMatchOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteGroupAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金支出表<BR> 更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateGroupAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出表<BR> 批量更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
}
