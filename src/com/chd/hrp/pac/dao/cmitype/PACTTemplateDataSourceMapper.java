package com.chd.hrp.pac.dao.cmitype;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;



public interface PACTTemplateDataSourceMapper extends SqlMapper {

	/**
	 * 
	 * 增加
	 */
	public int addBatchPACTTemplateDataSource(List<?> entityMapp)throws DataAccessException;
	
	/**
	 * 
	 * 更新
	 */
	public int updatePACTTemplateDataSource(List<?> entityMapp)throws DataAccessException;
	

	/**
	 * 
	 * 删除
	 */
	public int deletePACTTemplateDataSource(List<?> entityMap)throws DataAccessException;
	
	/**
	 * 
	 * 查询
	 */	
	public List<Map<String, Object>> queryPACTTSourceByCode(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryPACTTSourceByCode(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public Integer queryMaxrowid(Map<String, Object> map);
	public void deleteByCSCode(Map<String, Object> map);
}
