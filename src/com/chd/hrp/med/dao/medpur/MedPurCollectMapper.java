package com.chd.hrp.med.dao.medpur;

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
public interface MedPurCollectMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 采购汇总查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedPurCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购汇总查询 分页查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedPurCollect(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 采购汇总查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedPurCollectType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购汇总查询 分页查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedPurCollectType(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
