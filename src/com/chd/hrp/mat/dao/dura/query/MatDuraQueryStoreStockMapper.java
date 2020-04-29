package com.chd.hrp.mat.dao.dura.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 耐用品在库库存查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatDuraQueryStoreStockMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryStoreStock(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryStoreStock(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryScrapDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryScrapDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询仓库<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryScrapDetailStore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分页查询仓库<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDuraQueryScrapDetailStore(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
}
