package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SysHisDeptMapper extends SqlMapper{

	/**
	 * 
	* @Title: addBatchSysHisDept
	* @Description: 批量添加
	* @param @param list
	* @param @return
	* @param @throws DataAccessException
	* @return int 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public int addBatchSysHisDept(List<Map<String,Object>> list)throws DataAccessException;
	
	
	/**
	 * 
	* @Title: querySysHisDeptByCode
	* @Description: bycode
	* @param @param entityMap
	* @param @return
	* @param @throws DataAccessException
	* @return Map<String,Object> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public Map<String, Object> querySysHisDeptByCode(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	* @Title: querySysHisDept
	* @Description: 
	* @param @param entityMap
	* @param @param rowBounds
	* @param @return
	* @param @throws DataAccessException
	* @return List<Map<String,Object>> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public List<Map<String, Object>> querySysHisDept(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 
	* @Title: querySysHisDept
	* @Description: 
	* @param @param entityMap
	* @param @param rowBounds
	* @param @return
	* @param @throws DataAccessException
	* @return List<Map<String,Object>> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public List<Map<String, Object>> querySysHisDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	* @Title: deleteBatchSysHisDept
	* @Description: 
	* @param @param list
	* @param @return
	* @param @throws DataAccessException
	* @return int 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public int deleteBatchSysHisDept(List<Map<String,Object>> list) throws DataAccessException;
}
