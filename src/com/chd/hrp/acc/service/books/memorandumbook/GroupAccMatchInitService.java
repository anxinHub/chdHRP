package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMatchInit;

public interface GroupAccMatchInitService {
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 添加AccMatchInit
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量添加AccMatchInit
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public String queryGroupAccMatchInit(Map<String,Object> entityMap) throws DataAccessException;
	
	//配套资金初始账  打印
	public List<Map<String, Object>> queryGroupAccMatchInitPrint(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchInit queryGroupAccMatchInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
}
