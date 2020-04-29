package com.chd.hrp.mat.dao.matpur;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
/**
 * @Description:
 * 采购汇总查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */
public interface MatPurCollectMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 材料采购查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInvPurMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatInvPurMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 采购汇总查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatPurCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购汇总查询 分页查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatPurCollect(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 采购汇总查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatPurCollectType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购汇总查询 分页查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatPurCollectType(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
