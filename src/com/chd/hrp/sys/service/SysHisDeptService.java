package com.chd.hrp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface SysHisDeptService {
  /**
	* 
	* @Title: querySysHisDept
	* @Description: 查询
	* @param @param entityMap
	* @param @return
	* @param @throws DataAccessException
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public String querySysHisDept(Map<String,Object> entityMap) throws DataAccessException;
	
   /**
	* 
	* @Title: deleteSysHisDept
	* @Description: 批量删除
	* @param @param list
	* @param @return
	* @param @throws DataAccessException
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public String deleteBatchSysHisDept(List<Map<String,Object>> list) throws DataAccessException;
	
	
	public String importSysHisDept(Map<String,Object> entityMap) throws DataAccessException;
	
}
