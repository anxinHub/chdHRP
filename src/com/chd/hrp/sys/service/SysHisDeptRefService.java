package com.chd.hrp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SysHisDeptRefService {

    /**
    * 
    * @Title: addSysHisDeptRef
    * @Description:添加 
    * @param @param mapVo
    * @param @return
    * @param @throws DataAccessException
    * @return String 
    * @date 2020年2月17日   
    * @author sjy
     */
	public String addSysHisDeptRef(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	* @Title: querySysHisDeptRef
	* @Description: 查询
	* @param @param mapVo
	* @param @return
	* @param @throws DataAccessException
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	public String querySysHisDeptRef(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	 * @Title: deleteBatchSysHisDeptRef
	 * @Description: 
	 * @param @return
	 * @return String 
	 * @date 2020年2月17日   
	 * @author sjy
	 */
	public String deleteBatchSysHisDeptRef(List<Map<String, Object>> list) throws DataAccessException;
}
