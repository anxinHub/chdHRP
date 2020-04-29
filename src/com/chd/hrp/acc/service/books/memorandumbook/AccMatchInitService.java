package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchInit;

public interface AccMatchInitService {
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 添加AccMatchInit
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量添加AccMatchInit
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryAccMatchInit(Map<String,Object> entityMap) throws DataAccessException;
	
	//配套资金初始账  打印
	public List<Map<String, Object>> queryAccMatchInitPrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchInit queryAccMatchInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
}
