package com.chd.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;


public interface SqlMapper {
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatch(List<?> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int update(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新数据<BR> 
	 * @param  entityMap
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public int updateBatch(List<?> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int delete(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @param <T>
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的条件
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取对象结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的条件
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException;
	
}
